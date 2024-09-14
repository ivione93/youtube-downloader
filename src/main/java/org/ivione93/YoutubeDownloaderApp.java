package org.ivione93;

import javafx.geometry.Insets;
import org.jboss.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class YoutubeDownloaderApp extends Application {

    @Override
    public void start(Stage stage) {
        Label urlLabel = new Label("Introduce la URL del álbum de YouTube:");
        TextField urlTextField = new TextField();

        Button downloadButton = new Button("Descargar");
        downloadButton.setOnAction(event -> {
            String url = urlTextField.getText();
            if (!url.isEmpty()) {
                startDownload(url);  // Iniciar la descarga con Selenium
            } else {
                Logger.getLogger(YoutubeDownloaderApp.class.getName()).log(Logger.Level.INFO, "Por favor, introduce una URL válida.");
            }
        });

        VBox vbox = new VBox(10, urlLabel, urlTextField, downloadButton);
        VBox.setMargin(urlLabel, new Insets(30, 10, 0, 10));
        VBox.setMargin(urlTextField, new Insets(10, 10, 0, 10));
        VBox.setMargin(downloadButton, new Insets(10));
        Scene scene = new Scene(vbox, 400, 200);
        stage.setTitle("Descargador de Canciones de YouTube");
        stage.setScene(scene);
        stage.show();
    }

    private void startDownload(String url) {
        Logger.getLogger(YoutubeDownloader.class.getName()).log(Logger.Level.INFO, "Descargando álbum de YouTube desde: " + url);
        YoutubeDownloader youtubeDownloader = new YoutubeDownloader();
        youtubeDownloader.youtubeDownloader(url);
    }
}
