package duke;

public class Duke {
    private Storage taskStorage;
    private Storage contactStorage;
    private Ui ui;
    private Parser parser;
    private TaskList tasks;
    private ContactList contactList;

    public Duke() {
        this.taskStorage = new Storage("C:/Users/Jeremias/Documents/GitHub/ip/data/", "task.txt");
        this.contactStorage = new Storage("C:/Users/Jeremias/Documents/GitHub/ip/data/", "contact.txt");
        this.ui = new Ui();
        this.parser = new Parser();
        this.tasks = taskStorage.loadTaskList();
        this.contactList = contactStorage.loadContactList();
    }

    public static void main(String[] args) {
    }


    /**
     * Executes the command that the user inputs.
     *
     * @param input User input.
     * @return String of command executed.
     */
    private String executeCommand(String input) {
        String command = parser.parseCommand(input);
        Command cmd;
        try {
            if (command.equals("todo") || command.equals("deadline") || command.equals("event")) {
                cmd = new AddTaskCommand(command, input, tasks);
            } else if (command.equals("list")) {
                cmd = new ListTaskCommand(command, input, tasks);
            } else if (command.equals("done")) {
                cmd = new DoneTaskCommand(command, input, tasks);
            } else if (command.equals("delete")) {
                cmd = new DeleteTaskCommand(command, input, tasks);
            } else if (command.equals("help")) {
                cmd = new HelpCommand(command, input, tasks);
            } else if (command.equals("find")) {
                cmd = new FindCommand(command, input, tasks);
            } else if (command.equals("bye")) {
                cmd = new ByeCommand(command, input, tasks);
            } else if (command.equals("contact add")) {
                cmd = new AddContactCommand(command, input, tasks, contactList);
            } else if (command.equals("contact list")) {
                cmd = new ListContactCommand(command, input, tasks, contactList);
            } else if (command.equals("contact delete")) {
                cmd = new DeleteContactCommand(command, input, tasks, contactList);
            } else {
                throw new WrongCommandDukeException();
            }
        } catch (DukeException e) {
            return ui.getError(e);
        }
        String str = "";
        if (cmd != null) {
            str = cmd.execute();
            tasks = cmd.getTaskList();
            save();
        }
        return str;
    }

    /**
     * Saves the task list into the storage file.
     */
    private void save() {
        String taskStr = "";
        for (int i = 0; i < tasks.getSize(); i++) {
            taskStr += tasks.getTask(i).formatToSave() + "\n";
        }
        taskStorage.saveTaskList(taskStr);
        String contactStr = "";
        for (int i = 0; i < contactList.getSize(); i++) {
            contactStr += contactList.getContact(i).formatToSave() + "\n";
        }
        contactStorage.saveContactList(contactStr);
    }

    /**
     * Greets the user.
     *
     * @return String of greeting.
     */
    public static String greeting() {
        return new Ui().getGreeting();
    }

    /**
     * Returns help message.
     *
     * @return String of help message.
     */
    public static String help() {
        return new Ui().getHelp();
    }

    /**
     * Gets String of command executed.
     *
     * @param input User input.
     * @return String of command executed.
     */
    public String getResponse(String input) {
        try {
            return executeCommand(input);
        } catch (Exception e) {
            return ui.getError(e);
        }
    }
}