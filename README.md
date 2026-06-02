# Domus Control - Home Assistant

Smart device management system developed as part of the **Object-Oriented Programming (OOP)** course at the University of Minho (2025/2026).

**Domus Control** allows users to manage their smart homes, organizing them into divisions and associating various types of connected devices, with support for automations, scenarios, and time-based scheduling.

## 🚀 Key Features

* **Entity Management**: Creation and management of Users, Houses, Divisions, and Devices.
* **Device Hierarchy**: Support for Lamps, Smart Plugs, Curtains, Speakers, Garage Gates, and Sensors (Light and Water).
* **Automation Engine**: Configuration of rules based on conditions (e.g., detecting rain or low light) that trigger automatic actions.
* **Scenarios**: Execution of predefined sets of actions (e.g., "Movie Mode", "Leaving Home").
* **Schedules**: Scheduling of one-time or time-interval actions.
* **Data Persistence**: Saving and loading the application's full state through object serialization.
* **Statistics**: Analysis of energy consumption, most-used devices, and divisions with the highest hardware density.

## 🛠️ Technologies Used

* **Language**: Java (JDK 17 or higher).
* **Unit Testing**: JUnit 5.
* **Build Management**: Makefile.
* **Documentation**: Javadoc.

## 🏗️ System Architecture

The project follows the **MVC (Model-View-Controller)** pattern to ensure separation of concerns:
* **Model**: Base entities and business logic (`User`, `House`, `Device`, `Automation`).
* **View**: Interactive command-line interface (CLI) with ANSI color support and dynamic dashboards.
* **Controller**: `DomusControl` central class that acts as a facade for state management and rule execution.

### Applied OOP Concepts:
* **Encapsulation**: Private attributes with access through public methods and "Most Qualified Class" principles.
* **Inheritance and Polymorphism**: Abstract hierarchy of devices and actions.
* **Abstraction**: Use of interfaces for conditions and abstract classes for base components.

## 🧪 Testing and Robustness

The project includes a suite of **153 unit tests** with a 100% success rate, covering:
* Model and controller logic.
* Automation engine and complex rules.
* Interface and input validation.
* **Stress Tests**: Verification of resilience with 5,000 users, 500 houses, 1000 divisons and 5,000 devices, along with memory leak testing and fuzzing with giant strings.

## 📋 How to Run

Ensure you have Java installed.

1. **Compile the project**:
```Bash
make compile
```
Run the application:

```Bash
make run
```
Run tests:

```Bash
make test
```

📁 Directory Structure
```Plaintext
.
├── src/
│   ├── main/
│   │   ├── automacao/    # Rule logic, scenarios, and schedules
│   │   ├── controller/   # DomusControl Facade
│   │   ├── Exceptions/   # Custom exceptions
│   │   ├── model/        # Domain entities
│   │   └── view/         # CLI Interface and Menu
│   └── test/             # JUnit 5 test suite
├── bin/                  # Compiled files
├── lib/                  # External libraries (JUnit)
├── Makefile              # Task automation
└── README.md
```

👥 Authors - Group 8
Carlos Martins (A109507)

Diogo Vieira (A109744)

Tomás Machado (A104186)

Project developed for the Object-Oriented Programming course - University of Minho.
