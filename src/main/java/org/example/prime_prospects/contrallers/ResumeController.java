package org.example.prime_prospects.contrallers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.prime_prospects.JavaFxApplication;
import org.example.prime_prospects.dataFormats.Resume;

public class ResumeController {
    private JavaFxApplication app;
    private Resume it;

    public void setApp(JavaFxApplication mainApp){
        app = mainApp;
    }

    public void setResume(Resume item){
        it = item;
        positionText.setText(it.position);
        nameText.setText(it.name);
        salaryText.setText("" + it.expectedSalary);
        workYears.setText("" + it.workYears);
        skillsText.setText(it.skills);
        additionalSkillsText.setText(it.additionalSkills);
        aboutMeText.setText(it.aboutMe);
        addressText.setText(it.site);
        contacts.setText(it.contactsOwner);
    }

    @FXML
    private VBox center;

    @FXML
    private Label positionText;

    @FXML
    private Label nameText;

    @FXML
    private Label workYears;

    @FXML
    private Label salaryText;

    @FXML
    private Text skillsText;

    @FXML
    private Text additionalSkillsText;

    @FXML
    private Text aboutMeText;

    @FXML
    private Label addressText;

    @FXML
    private Label contacts;

    @FXML
    private void backClick(){
        app.showMainView();
    }
}
