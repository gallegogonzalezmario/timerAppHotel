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

/**
 * Esta clase es un temporizador gráfico que realiza una cuenta atrás con las siguientes
 * características:
 * <p>
 * - Se basa en una etiqueta que muestra la cuenta atrás del temporizador.
 * <p>
 * - Tiene un atributo tiempo, de tipo IntegerProperty, que va a registrar cada cambio de valor para
 * modificar el texto de la etiqueta.
 * <p>
 * - Cuando termina, lanza un evento que se puede manejar.
 *
 *
 * @author  Mario Gallego González
 * @version 1.0
 * @since JDK-17
 */
public class TimerAppHotel extends Label {

    private IntegerProperty tiempo = new SimpleIntegerProperty(90);
    private Timeline tm = new Timeline();


    /**
     * Método que inicializa TimerAppHotel. Este método es necesario para que la animación
     * de TimerAppHotel funcione.
     *
     * @see #comienzaTemporizador()
     */
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


    /**
     * Método que comienza la cuenta atrás de TimerAppHotel. Su valor por defecto
     * es 90 segundos, por lo que hay que modificar el valor de tiempo antes de llamar este
     * método.
     *
     *
     * @see #iniciaTemporizador()
     * @see #setTiempo(int)
     * @see #pausarTemporizador()
     */
    public void comienzaTemporizador(){

        KeyValue kv = new KeyValue(tiempo, 0);
        KeyFrame kf = new KeyFrame(Duration.millis(tiempo.get() * 1000), kv);

        tm.getKeyFrames().add(kf);
        tm.play();
    }


    /**
     * Maneja el evento que lanza TimerAppHotel cuando termina.
     * @param onFinished Manejador de evento que lanza el temporizador
     */
    public void setOnFinished(EventHandler<ActionEvent> onFinished){
        tm.setOnFinished(onFinished);
    }

    /**
     * Pausa la cuenta atrás de TimerAppHotel.
     *
     * @see #resumirTemporizador()
     */
    public void pausarTemporizador(){
        tm.pause();
    }

    /**
     * Resume la cuenta atrás del temporizador.
     */
    public void resumirTemporizador(){
        tm.play();
    }

    /**
     * Actualiza el valor de tiempo de TimerAppHotel.
     * <p>
     * Si se quiere ver el cambio en el temporizador, hay que ejecutar
     * de nuevo el método comienzaTemporizador().
     * @param tiempo Nuevo valor de tiempo
     *
     * @see #comienzaTemporizador()
     *
     */
    public void setTiempo(int tiempo){
        this.tiempo.set(tiempo);
    }

    /**
     * Devuelve el valor tipo int del tiempo de TimerAppHotel.
     * @return Valor de tiempo de TimerAppHotel.
     */
    public int getTiempo(){ return tiempo.get(); }
}
