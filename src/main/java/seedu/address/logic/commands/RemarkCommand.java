package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Remark;

import java.util.List;

/**
 * Changes the remark of an existing patient in the address book.
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the patient identified "
            + "by the index number used in the last patient listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "r/ [REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "r/ Likes to swim.";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Remark command not implemented yet";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Remark: %2$s";
    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Patient: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Patient: %1$s";

    private final Index index;
    private final Remark remark;

    /**
     * @param index of the patient in the filtered patient list to edit the remark
     * @param remark of the patient to be updated to
     */
    public RemarkCommand(Index index, Remark remark) {
        requireAllNonNull(index, remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Patient> lastShownList = model.getFilteredPatientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patientToEdit = lastShownList.get(index.getZeroBased());
        Patient editedPatient = new Patient(patientToEdit.getName(), patientToEdit.getBirthdate(), patientToEdit.getPhone(), patientToEdit.getEmail(),
                patientToEdit.getAddress(), remark, patientToEdit.getTags());

        model.setPatient(patientToEdit, editedPatient);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);

        return new CommandResult(generateSuccessMessage(editedPatient));
    }

    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code patientToEdit}.
     */
    private String generateSuccessMessage(Patient patientToEdit) {
        String message = !remark.value.isEmpty() ? MESSAGE_ADD_REMARK_SUCCESS : MESSAGE_DELETE_REMARK_SUCCESS;
        return String.format(message, patientToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index)
                && remark.equals(e.remark);
    }



}