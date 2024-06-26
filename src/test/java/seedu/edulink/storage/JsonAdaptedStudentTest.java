package seedu.edulink.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.edulink.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.edulink.testutil.Assert.assertThrows;
import static seedu.edulink.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.edulink.commons.exceptions.IllegalValueException;
import seedu.edulink.model.student.Address;
import seedu.edulink.model.student.Email;
import seedu.edulink.model.student.Id;
import seedu.edulink.model.student.Intake;
import seedu.edulink.model.student.Major;
import seedu.edulink.model.student.Name;
import seedu.edulink.model.student.Phone;

public class JsonAdaptedStudentTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_ID = "15151gaa";
    private static final String INVALID_MAJOR = "15151";
    private static final String INVALID_INTAKE = "15151";

    private static final String VALID_ID = BENSON.getId().toString();
    private static final String VALID_MAJOR = BENSON.getMajor().toString();
    private static final String VALID_INTAKE = BENSON.getIntake().toString();
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
        .map(JsonAdaptedTag::new)
        .collect(Collectors.toList());
    private static final List<JsonAdaptedGrade> VALID_GRADES = BENSON.getGrades().stream()
        .map(JsonAdaptedGrade::new)
        .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_ID, VALID_MAJOR, VALID_INTAKE, INVALID_NAME,
                VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_GRADES);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(INVALID_ID, VALID_MAJOR, VALID_INTAKE, VALID_NAME,
                VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_GRADES);
        String expectedMessage = Id.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_ID, VALID_MAJOR, VALID_INTAKE, null,
            VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_GRADES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_MAJOR, VALID_INTAKE, VALID_NAME,
            VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_GRADES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Id.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidMajor_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_ID, INVALID_MAJOR, VALID_INTAKE, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_GRADES);
        String expectedMessage = Major.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullMajor_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_ID, null, VALID_INTAKE, VALID_NAME,
            VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_GRADES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Major.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidIntake_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_ID, VALID_MAJOR, INVALID_INTAKE, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_GRADES);
        String expectedMessage = Intake.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullIntake_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_ID, VALID_MAJOR, null, VALID_NAME,
            VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_GRADES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Intake.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_ID, VALID_MAJOR, VALID_INTAKE, VALID_NAME, INVALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_GRADES);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_ID, VALID_MAJOR, VALID_INTAKE, VALID_NAME, null,
            VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_GRADES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_ID, VALID_MAJOR, VALID_INTAKE, VALID_NAME, VALID_PHONE, INVALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_GRADES);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_ID, VALID_MAJOR, VALID_INTAKE, VALID_NAME,
            VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS, VALID_GRADES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_ID, VALID_MAJOR, VALID_INTAKE, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                INVALID_ADDRESS, VALID_TAGS, VALID_GRADES);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_ID, VALID_MAJOR, VALID_INTAKE, VALID_NAME,
            VALID_PHONE, VALID_EMAIL, null, VALID_TAGS, VALID_GRADES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
            new JsonAdaptedPerson(VALID_ID, VALID_MAJOR, VALID_INTAKE, VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, invalidTags, VALID_GRADES);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
