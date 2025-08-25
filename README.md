
## Banking Transfer Syste

### Project Overview
A banking system designed to process financial transfer operations from text files. The system handles account management, transfer processing, and report generation.

### Key Features
* **Account Management** - creation, storage, and balance management of bank accounts
* **Transfer Processing** - handling of financial transactions between accounts
* **File Processing** - reading and processing transfer requests from files
* **Error Handling** - comprehensive validation and error reporting
* **Reporting** - generation of detailed operation reports

### System Architecture

#### Main Components
* **TransferService** - core service for processing transfer operations
* **Account** - represents a bank account with balance information
* **TransferOperation** - encapsulates details of a single transfer operation
* **DateTimeUtils** - utility for date and time operations
* **FileUtils** - utility for file operations
* **ReportService** - handles report generation
* **TransferException** - custom exception handling for transfer errors

### Directory Structure
* **accounts/** - directory containing account data files
* **input/** - directory for incoming transfer files
* **archive/** - directory for processed files
* **report.txt** - output report file

### Getting Started

#### Prerequisites
* Java Development Kit (JDK) 8 or higher
* Build Tool (Maven/Gradle recommended)

#### Installation
1. Clone the repository
2. Set up the project directory structure
3. Configure environment variables if necessary

### Usage

#### Running the Application
1. Place transfer files in the **input** directory
2. Ensure **accounts** directory contains valid account data
3. Execute the **Main** class
4. Use the menu options:
   * `1` - Process transfer files
   * `2` - View report
   * `0` - Exit

### File Formats

#### Account File Format
Each line should contain:
```
account_number balance
Example:
12345-67890 1000.00
```

#### Transfer File Format
Each line should contain:
```
from_account to_account amount
Example:
12345-67890 54321-98765 100.50
```

### Error Handling
The system handles the following errors:
* Invalid account format
* Missing accounts in the database
* Insufficient funds
* Invalid transfer amounts
* File read/write errors

### Contributing
Contributions are welcome! You can contribute by:
* Reporting bugs
* Suggesting improvements
* Adding new features
* Updating documentation

### License
This project is licensed under the MIT License.

### Contact
For any inquiries or suggestions, please contact the project maintainers.
