package seedu.edulink.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.edulink.commons.core.GuiSettings;
import seedu.edulink.commons.core.LogsCenter;
import seedu.edulink.commons.util.CounterUtil;
import seedu.edulink.logic.commands.Command;
import seedu.edulink.logic.commands.CommandResult;
import seedu.edulink.logic.commands.exceptions.CommandException;
import seedu.edulink.logic.parser.AddressBookParser;
import seedu.edulink.logic.parser.exceptions.ParseException;
import seedu.edulink.model.Model;
import seedu.edulink.model.ReadOnlyAddressBook;
import seedu.edulink.model.RecentCommands;
import seedu.edulink.model.student.Student;
import seedu.edulink.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
        "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private int recentCommandCounter = 0;
    private int detailsIndex = 0;

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;
    private final RecentCommands recentCommands;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser(storage);
        recentCommands = new RecentCommands();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);
        recentCommandCounter = 0;
        detailsIndex = 0;
        recentCommands.add(commandText);
        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }
        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Student> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public ObservableList<String> getRecentCommands() {
        return recentCommands.getCommands();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public int getRecentCommandsCounter(int limit) {
        int previousCommandCounter = recentCommandCounter;
        recentCommandCounter = CounterUtil.incrementCounter(recentCommandCounter, limit);
        return previousCommandCounter;
    }

    @Override
    public int getDetailsIndex(boolean isIncrement, int limit) {
        if (isIncrement) {
            detailsIndex = CounterUtil.incrementCounter(detailsIndex, limit);
        } else {
            detailsIndex = CounterUtil.decrementCounter(detailsIndex, limit);
        }
        return detailsIndex;
    }
}
