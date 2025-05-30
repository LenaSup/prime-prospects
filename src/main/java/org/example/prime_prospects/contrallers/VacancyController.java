package org.example.prime_prospects.contrallers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.prime_prospects.JavaFxApplication;
import org.example.prime_prospects.dataFormats.Vacancy;


public class VacancyController {
    private JavaFxApplication app;
    private Vacancy it;

    public void setApp(JavaFxApplication mainApp){app = mainApp;}

    public void setVacancy(Vacancy item){
        it = item;
        positionText.setText(it.position);
        workExperienceYearsText.setText("" + it.workExperienceYears);
        salaryText.setText("" + it.salary);
        requirementsText.setText(it.requirements);
        whatWeOfferText.setText(it.whatWeOffer);
        additionalSkillsText.setText(it.additionalSkills);
        employmentTypeText.setText(it.employmentType);
        scheduleText.setText(it.schedule);
        workingHoursText.setText("" + it.workingHours);
        addressText.setText(it.address);
        ownerText.setText(it.owner);
        owner_descriptionText.setText(it.owner_description);
        contacts.setText(it.contactsOwner);
    }
    @FXML
    private VBox center;

    @FXML
    private Label positionText;

    @FXML
    private Label workExperienceYearsText;

    @FXML
    private Label salaryText;

    @FXML
    private Text requirementsText;

    @FXML
    private Text whatWeOfferText;

    @FXML
    private Text additionalSkillsText;

    @FXML
    private Label employmentTypeText;

    @FXML
    private Label scheduleText;

    @FXML
    private Label workingHoursText;

    @FXML
    private Label addressText;

    @FXML
    private Label ownerText;

    @FXML
    private Text owner_descriptionText;

    @FXML
    private Label contacts;

    @FXML
    private void backClick(){
        System.out.println("Buuuuuup");
        app.showMainView();
    }
}
