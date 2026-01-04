package org.example.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.example.controller.Controller;
import org.example.exception.MyException;
import org.example.model.PrgState;
import org.example.model.adt.*;
import org.example.model.stmt.IStmt;
import org.example.model.value.Value;
import org.example.repository.IRepository;
import org.example.repository.Repository;

import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Collectors;

public class GUIController {
    private final BorderPane root;
    private final ListView<String> programsListView;
    private final Button runOneStepButton;

    // main window controls
    private final TextField nrPrgStatesField;
    private final TableView<Map.Entry<Integer, Value>> heapTable;
    private final ListView<String> outListView;
    private final ListView<String> fileTableListView;
    private final ListView<String> prgIdsListView;
    private final TableView<Map.Entry<String, Value>> symTableView;
    private final ListView<String> exeStackListView;

    // example statements
    private final IStmt[] examples;

    // runtime pieces
    private Controller controller;
    private IRepository repo;

    public GUIController(IStmt[] examples) {
        this.examples = examples;
        root = new BorderPane();
        programsListView = new ListView<>();
        runOneStepButton = new Button("Run one step");

        nrPrgStatesField = new TextField();
        nrPrgStatesField.setEditable(false);

        heapTable = new TableView<>();
        outListView = new ListView<>();
        fileTableListView = new ListView<>();
        prgIdsListView = new ListView<>();
        symTableView = new TableView<>();
        exeStackListView = new ListView<>();

        buildUI();
        populatePrograms();
        attachHandlers();
    }

    public Controller getController() { return controller; }

    public Parent getRoot() { return root; }

    private void buildUI() {
        // create a top title
        Label title = new Label("Toy Interpreter - GUI");
        title.setStyle("-fx-font-size:16px; -fx-font-weight:bold;");
        BorderPane.setMargin(title, new Insets(10));
        root.setTop(title);

        // left: program selector
        VBox left = new VBox(10);
        left.setPadding(new Insets(10));
        Label lbl = new Label("Select program to run:");
        programsListView.setPrefWidth(350);
        programsListView.setPrefHeight(500);
        left.getChildren().addAll(lbl, programsListView);

        // right: details
        VBox right = new VBox(10);
        right.setPadding(new Insets(10));

        HBox topRow = new HBox(10);
        topRow.getChildren().addAll(new Label("Nr PrgStates:"), nrPrgStatesField, runOneStepButton);

        Label heapLabel = new Label("Heap (addr -> value):");
        heapTable.setPrefHeight(150);
        heapTable.setPrefWidth(400);
        // heap table columns
        TableColumn<Map.Entry<Integer, Value>, String> heapAddrCol = new TableColumn<>("Address");
        heapAddrCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(String.valueOf(c.getValue().getKey())));
        TableColumn<Map.Entry<Integer, Value>, String> heapValCol = new TableColumn<>("Value");
        heapValCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(String.valueOf(c.getValue().getValue())));
        heapTable.getColumns().addAll(heapAddrCol, heapValCol);
        heapTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        heapTable.setPlaceholder(new Label("Heap is empty"));

        Label outLabel = new Label("Out:");
        outListView.setPrefHeight(80);

        Label fileLabel = new Label("FileTable:");
        fileTableListView.setPrefHeight(80);

        Label idsLabel = new Label("Prg IDs:");
        prgIdsListView.setPrefHeight(80);

        Label symLabel = new Label("SymTable (var -> val):");
        symTableView.setPrefHeight(150);
        symTableView.setPrefWidth(300);
        // sym table columns
        TableColumn<Map.Entry<String, Value>, String> symVarCol = new TableColumn<>("Var");
        symVarCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getKey()));
        TableColumn<Map.Entry<String, Value>, String> symValCol = new TableColumn<>("Value");
        symValCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(String.valueOf(c.getValue().getValue())));
        symTableView.getColumns().addAll(symVarCol, symValCol);
        symTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        symTableView.setPlaceholder(new Label("Symbol table is empty"));

        Label exeLabel = new Label("ExeStack:");
        exeStackListView.setPrefHeight(150);

        // assemble right side
        right.getChildren().addAll(topRow, heapLabel, heapTable, outLabel, outListView, fileLabel, fileTableListView, idsLabel, prgIdsListView, symLabel, symTableView, exeLabel, exeStackListView);

        SplitPane split = new SplitPane();
        split.getItems().addAll(left, right);
        split.setDividerPositions(0.35);

        root.setCenter(split);
    }

    private void populatePrograms() {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < examples.length; i++) {
            items.add((i + 1) + ": " + examples[i].toString());
        }
        programsListView.setItems(items);

        // auto-select the first program so the UI shows something immediately
        if (!items.isEmpty()) {
            programsListView.getSelectionModel().select(0);
            try {
                prepareRepoForExample(0);
                updateAll();
            } catch (MyException e) {
                showError(e.getMessage());
            }
        }
    }

    private void attachHandlers() {
        programsListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldV, newV) -> {
            int idx = newV.intValue();
            if (idx >= 0 && idx < examples.length) {
                try {
                    prepareRepoForExample(idx);
                    updateAll();
                } catch (MyException e) {
                    showError(e.getMessage());
                }
            }
        });

        prgIdsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            updateSymAndStackForSelectedId();
        });

        runOneStepButton.setOnAction(evt -> {
            if (controller == null) { showError("No program selected"); return; }
            try {
                // run one step for all non-completed programs
                List<PrgState> all = controller.getRepo().getPrgList();
                List<PrgState> toRun = controller.removeCompletedPrg(all);
                if (toRun.isEmpty()) {
                    return;
                }
                controller.oneStepForAllPrg(toRun);
                updateAll();
            } catch (MyException e) {
                showError(e.getMessage());
            }
        });
    }

    private void prepareRepoForExample(int idx) throws MyException {
        IStmt selected = examples[idx];
        // create fresh program state
        MyIStack<org.example.model.stmt.IStmt> stk = new MyStack<>();
        MyIDictionary<String, Value> sym = new MyDictionary<>();
        MyIList<Value> out = new MyList<>();
        MyIFileTable<String, BufferedReader> ft = new MyFileTable<>();
        MyIHeap<Integer, Value> heap = new MyHeap();

        PrgState prg = new PrgState(stk, sym, out, ft, heap, selected);
        repo = new Repository(prg, "log.txt");
        controller = new Controller(repo);
    }

    private void updateAll() {
        if (repo == null) return;
        List<PrgState> prgList = repo.getPrgList();

        // nr prg states
        nrPrgStatesField.setText(String.valueOf(prgList.size()));

        // heap
        if (!prgList.isEmpty()) {
            Map<Integer, Value> heap = prgList.get(0).getHeap().getContent();
            ObservableList<Map.Entry<Integer, Value>> heapEntries = FXCollections.observableArrayList(heap.entrySet());
            heapTable.setItems(heapEntries);
        } else {
            heapTable.setItems(FXCollections.observableArrayList());
        }

        // out
        List<String> outList = prgList.stream()
                .flatMap(p -> p.getOut().getList().stream())
                .map(Object::toString)
                .collect(Collectors.toList());
        outListView.setItems(FXCollections.observableArrayList(outList));

        // file table
        List<String> files = prgList.stream()
                .flatMap(p -> p.getFileTable().getContent().keySet().stream())
                .map(Object::toString)
                .collect(Collectors.toList());
        fileTableListView.setItems(FXCollections.observableArrayList(files));

        // prg ids
        List<String> ids = prgList.stream().map(p -> String.valueOf(p.getId())).collect(Collectors.toList());
        prgIdsListView.setItems(FXCollections.observableArrayList(ids));

        // ensure a program id is selected so sym table and exe stack are shown
        if (!ids.isEmpty() && prgIdsListView.getSelectionModel().isEmpty()) {
            prgIdsListView.getSelectionModel().select(0);
        }

        updateSymAndStackForSelectedId();
    }

    private void updateSymAndStackForSelectedId() {
        String sel = prgIdsListView.getSelectionModel().getSelectedItem();
        if (sel == null || repo == null) {
            symTableView.setItems(FXCollections.observableArrayList());
            exeStackListView.setItems(FXCollections.observableArrayList());
            return;
        }
        int id = Integer.parseInt(sel);
        Optional<PrgState> opt = repo.getPrgList().stream().filter(p -> p.getId() == id).findFirst();
        if (!opt.isPresent()) return;
        PrgState p = opt.get();

        // sym table
        Map<String, Value> sym = p.getSymTable().getContent();
        ObservableList<Map.Entry<String, Value>> symEntries = FXCollections.observableArrayList(sym.entrySet());
        symTableView.setItems(symEntries);

        // exe stack: we want top element first
        List<String> elems = Arrays.stream(p.getStk().toFileString().split("\n"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
        Collections.reverse(elems); // because toFileString prints from bottom to top
        exeStackListView.setItems(FXCollections.observableArrayList(elems));
    }

    private void showError(String msg) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
            a.showAndWait();
        });
    }
}
