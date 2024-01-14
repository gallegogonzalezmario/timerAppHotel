package es.ieslosmontecillos.timerapphotel;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.IOException;

public class TimerAppHotel extends Label {
    private IntegerProperty tiempo = new SimpleIntegerProperty(90);
    private Timeline tm = new Timeline();

    public void iniciaTemporizador(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("timer-apphotel.fxml"));
        loader.setRoot(this);

        loader.setController(this);

        tiempo.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if(tiempo.get()/3600 > 0)
                    TimerAppHotel.this.setText("Quedan " + (tiempo.get()/3600) + " horas " + ((tiempo.get()%3600)/60) + " minutos " + (tiempo.get()%60) + " segundos");
                else if(tiempo.get()/60 > 0)
                    TimerAppHotel.this.setText("Tiene " + (tiempo.get()/60) + " minutos " + (tiempo.get()%60) + " segundos para rellenar la reserva.");
                else
                    TimerAppHotel.this.setText("Tiene " + (tiempo.get()%60) + " segundos para rellenar la reserva.");
            }
        });

        try {
            loader.load();
        } catch (IOException err) {
            throw new RuntimeException(err);
        }
    }

    public void comienzaTemporizador(){

        KeyValue kv = new KeyValue(tiempo, 0);
        KeyFrame kf = new KeyFrame(Duration.millis(tiempo.get() * 1000), kv);

        tm.getKeyFrames().add(kf);
        tm.play();
    }


    public void setOnFinished(EventHandler<ActionEvent> onFinished){
        tm.setOnFinished(onFinished);
    }
    public void pausarTemporizador(){
        tm.pause();
    }


    public void resumirTemporizador(){
        tm.play();
    }

    public void setTiempo(int tiempo){
        this.tiempo.set(tiempo);
    }
    public int getTiempo(){ return tiempo.get(); }
}
