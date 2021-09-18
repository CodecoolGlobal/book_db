# Book DB

## Story

Flynn, a local librarian, wants to digitalize his good old book register. Searching for records in a massive set of massive books is time-consuming.
Your task is to create a simple application to store books and authors in a PostgreSQL database, and to allow Flynn to add, update, and list all entries.


## What are you going to learn?

- Connect to a database using low-level technology (JDBC).
- Handle edge cases and exceptions.


## Tasks

1. Create a PostgreSQL database named `books` and import the schema and the data from `books.sql` file.
    - A database named `books` exists.
    - The `book` and `author` tables are created and filled with sample data.

2. Create a class named `BookDbManager` (in a subpackage named `manager`) with a `connect()` method that links to your `books` database and returns `DataSource`. Use `PGSimpleDataSource` to establish the connection.
    - A `BookDbManager` class exists, and its `connect()` method connects to the database and returns a `DataSource`.

3. Create the `AuthorDaoJdbc` and `BookDaoJdbc` classes in the `model` subpackage. Each one of them must accept a `DataSource` as a constructor parameter. Instances of JDBC DAOs are managed by `BookDbManager`.
    - The `AuthorDaoJdbc` and `BookDaoJdbc` classes are implemented.
    - The `AuthorDaoJdbc` and `BookDaoJdbc` classes use `PreparedStatement`s.
    - Exceptions are chained and thrown as RuntimeExceptions, to avoid "exception swallowing".

4. Make this app functional with a simple console UI. Implement `UserInterface` methods, and call them from `BookDbManager`, using a main menu to submenu workflow. Make sure that the user can list and edit all records, as well as add new ones.
    - The main menu with options `a - Authors`, `b - Books` and `q - Quit` navigates to the selected submenu and is functional.
    - The resources menu with options `l - List`, `a - Add`, `e - Edit` and `q - Quit` is functional on the selected entities.

## General requirements

None

## Hints

- You might need to change your Project Structure in IntelliJ to use JDK version 16 for Project SDK.
IntelliJ allows you to download it if you don't have it yet (Add SDK -> Download JDK).
- When you create a `dataSource` object that will be responsible for your connection to the database, remember to check whether the connection actually works. You can do it by invoking `dataSource.getConnection().close();`
- Remember to keep your queries SQL-injection-proof by using `java.sql.PreparedStatement`


## Background materials

- <i class="far fa-exclamation"></i> [PGSimpleDataSource example](https://www.programcreek.com/java-api-examples/index.php?api=org.postgresql.ds.PGSimpleDataSource)
- <i class="far fa-candy-cane"></i> [Book DB step-by-step project guide for Java](project/curriculum/materials/pages/java/book-db-java-guide.md)

