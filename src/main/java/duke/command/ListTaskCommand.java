package duke.command;

import duke.task.TaskList;
import duke.exception.DukeException;
import duke.exception.WrongFormatDukeException;

public class ListTaskCommand extends Command {

    public ListTaskCommand(String command, String input, TaskList taskList) {
        super(command, input, taskList);
    }

    /**
     * Checks if the user input is formatted into a correct list command.
     * If it is, it prints the task list.
     * Otherwise, it prints the error faced.
     */
    private void listTask() {
        try {
            if (parser.canParseListCommand(input)) {
                ui.printTaskList(taskList);
            } else {
                throw new WrongFormatDukeException(command);
            }
        } catch (DukeException e) {
            ui.printError(e);
        }
    }

    @Override
    public void execute() {
        listTask();
    }
}