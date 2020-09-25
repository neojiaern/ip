# User Guide
Duke is a program that allows users to track theirs tasks via the Command Line Interface (CLI).

## Quick Start

1. Ensure you have Java 11 or above installed in your Computer.
2. Download the latest ip.jar from [here](https://github.com/neojiaern/ip/releases).
3. Copy the jar file to the folder you want to use as the home folder for Duke.
4. Open a command window in that folder.
5. Run the command `java -jar ip.jar` and you should see the welcome message as shown below:
![Welcome message](images/welcome_message.PNG) <br />
with the message `(No previously loaded task from duke.txt)` above the Duke logo if this is your first time running Duke.
6. Type the command in the command line and type enter to execute it. <br />
Some example commands you can try:
* `list`: Lists all tasks.
* `todo assignment`: Adds assignment to the list of tasks.
* `done 1`: Marks the 1st task shown in the current list as completed.
* `delete 1`: Deletes the 1st task shown in the current list.
* `bye`: Exits the program.

Refer to [Features](#Features) below for details of each command.

## Features 

### Tracking of tasks
Allows users to track their tasks by viewing a list of current tasks.

### Automatic loading and saving of data
Previous tasks can be retrieved and loaded into Duke and any changes made will be saved automatically.

## Usage

### `todo` - Adding a todo task

Adds a new todo task to the current list of tasks.

Format: 

`todo TASK_DESCRIPTION`

Example of usage: 

`todo CS2113T revision`

Expected outcome:

```
Got it. I've added this task:
  [T][✘] CS2113T revision
Now you have 1 task(s) in the list.
```

### `deadline` - Adding a deadline task

Adds a new deadline task to the current list of tasks.

Format: 

`deadline TASK_DESCRIPTION /by DATE(YYYY-MM-DD) TIME(HH:mm)`
* The /by parameter with the DATE and TIME is compulsory.
* Ensure that DATE and TIME is in the correct format.

Example of usage: 

`deadline CS2113T assignment /by 2020-10-02 23:59`

Expected outcome:

```
Got it. I've added this task:
  [D][✘] CS2113T assignment (by: Oct 2 2020 23:59)
Now you have 2 task(s) in the list.
```

### `event` - Adding an event task

Adds a new event task to the current list of tasks.

Format: 

`event TASK_DESCRIPTION /at DATE(YYYY-MM-DD) TIME(HH:mm)`
* The /at parameter with the DATE and TIME is compulsory.
* Ensure that DATE and TIME are in the correct format.

Example of usage: 

`event project meeting /at 2020-10-01 14:00`

Expected outcome:

```
Got it. I've added this task:
  [E][✘] project meeting (at: Oct 1 2020 17:00)
Now you have 3 task(s) in the list.
```

### `list` - Listing all tasks

Lists all the current tasks.

Example of usage: 

`list`

Expected outcome:

```
Here are the task(s) in your list:
1.[T][✘] CS2113T revision
2.[D][✘] CS2113T assignment (by: Oct 2 2020 23:59)
3.[E][✘] project meeting (at: Oct 1 2020 17:00)
```

If there are no tasks in the list, you will get the output shown below instead:

`There is currently no task.`

### `find` - Locating tasks by keyword(s)

Locates a task based on keyword(s) provided.

Format: 

`find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g. `cs2113t` will match `CS2113T`
* Order of keywords does not matter. e.g. `CS2113T assignment` will match `assignment CS2113T`
* Only the task description is searched.
* Tasks matching at least one keyword will be returned. e.g. `CS2113T` will return `CS2113T assignment` and `CS2113T revision`

Example of usage: 

`find CS2113T`

Expected outcome:

```
Here are the matching task(s) in your list:
1.[T][✘] CS2113T revision
2.[D][✘] CS2113T assignment (by: Oct 2 2020 23:59)
```

### `due` - Locating a task due on a specific date

Locates a task due on the date specified.

Format:

`due DATE(YYYY-MM-DD)`
* DATE is a compulsory parameter.
* Ensure DATE is in the correct format.

Example of usage: 

`due 2020-10-02`

Expected outcome:

```
Here are the tasks due:
1.[D][✘] CS2113T assignment (by: Oct 2 2020 23:59)
```

### `done` - Marking a task as done

Marks a task as done based on specified index.

Format:

`done INDEX_OF_COMPLETED_TASK`
* Marks a task as done at the specified index.
* The index refers to the index number shown in the list.

Example of usage: 

`done 2`

Expected outcome:

```
Nice! I've marked this task as done:
  [D][✓] CS2113T assignment (by: Oct 2 2020 23:59)
```

### `delete` - Deleting a task

Deletes a task from the current list based on index provided.

Format: `delete INDEX_OF_TASK_TO_DELETE`
* Deletes a task at the specified index.
* The index refers to the index number shown in the list.

Example of usage: 

`delete 2`

Expected outcome:

```
Noted. I've removed this task:
  [D][✓] CS2113T assignment (by: Oct 2 2020 23:59)
Now you have 2 task(s) in the list.
```

### `bye` - Exiting the program

Exits the program.

Example of usage: 

`bye`

Expected outcome:

```
Bye. Hope to see you again soon!
```

### Saving the data
Duke data is automatically saved into data/duke.txt in the home folder ip.jar is in.

## Command summary

| **Action** | **Format, Examples** |
| ------------ | ------------- |
| Bye | `bye` |
| Deadline | `deadline TASK_DESCRIPTION /by DATE(YYYY-MM-DD) TIME(HH:mm)` <br> e.g. `deadline CS2113T assignment /by 2020-10-02 23:59` |
| Delete | `delete INDEX_OF_TASK_TO_DELETE` <br> e.g. `delete 2` |
| Done | `done INDEX_OF_COMPLETED_TASK` <br> e.g. `done 2` |
| Due | `due DATE(YYYY-MM-DD)` <br> e.g. `due 2020-10-02` |
| Event | `event TASK_DESCRIPTION /at DATE(YYYY-MM-DD) TIME(HH:mm)` <br> e.g. `event project meeting /at 2020-10-01 14:00` |
| Find | `find KEYWORD [MORE_KEYWORDS]` <br> `find CS2113T` |
| List | `list` |
| Todo | `todo TASK_DESCRIPTION` <br> e.g. `todo CS2113T revision` |
