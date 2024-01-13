module es.ieslosmontecillos.timerapphotel {
    requires javafx.controls;
    requires javafx.fxml;


    opens es.ieslosmontecillos.timerapphotel to javafx.fxml;
    exports es.ieslosmontecillos.timerapphotel;
}