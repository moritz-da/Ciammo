# Ciammo

- Amelie Pfänder
- Cindy Hoppmann
- Moritz Dallendörfer

## 1. Kurzbeschreibung

Ciammo ist ein WG-Manager, der es einer WG ermöglicht, ihr WG-Leben zu organisieren. Es können einzelne Nutzer angelegt werden, wobei jeder Nutzer entweder eine neue WG erstellen oder einer bestehenden WG beitreten kann. Es ist außerdem möglich, mehrere WGs und deren Nutzer unabhängig voneinander zu verwalten.

Sobald eine neue WG erstellt wird, wird automatisch eine Ausgabenliste, eine Einkaufsliste, ein Putzplan und eine Mitbewohnerliste für diese WG angelegt.

Bei der Ausgabenliste können Einkäufe und deren Kosten hinterlegt werden. Bei einem neuen Eintrag in die Liste wird außerdem automatisch der Nutzer hinterlegt, der diesen angelegt hat. Beim Filtern nach einem Nutzer wird die Gesamtsumme der Ausgaben des Nutzers angezeigt. Außerdem können ausgewählte, einzelne Zeilen oder die komplette Liste gelöscht werden.
Bei der Einkaufliste können Items hinzugefügt werden und diese, wenn gewünscht alphabetisch sortiert werden. Außerdem können auch hier ausgewählte-, einzelne Zeilen oder die komplette Liste gelöscht werden.

Beim Putzplan kann eine Aufgabe, die Kalenderwoche und ein Mitbewohner, der diese Aufgabe erledigen soll, hinzugefügt werden. Außerdem können auch hier ausgewählte-, einzelne Zeilen oder die komplette Liste gelöscht werden.

Bei der Mitbewohnerliste werden die Namen, E-Mail-Adressen und Geburtstage der Nutzer angezeigt, die man dort auch entfernen kann, falls ein Nutzer aus der WG auszieht. Hier lassen sich außerdem die WG-Zugangsdaten, also die WG-ID und das WG-Passwort anzeigen, welche zum Hinzufügen eines neuen Nutzers gebraucht werden.

In den Einstellungen von Ciammo lassen sich die Nutzerdaten, wie E-Mail-Adresse und Passwort, sowie die WG-Daten, wie WG-Name und Passwort ändern. Hier ist es auch möglich, den gerade angemeldeten Nutzer oder die gesamte WG zu löschen. Zusätzlich gibt es noch die Möglichkeit zwischen einem Tagmodus und einem Nachtmodus zu wählen.

## 1. Startklasse

Die Main-Methode befindet sich in der Klasse CiammoGUI.

## 3. Besonderheiten

Wie bereits in der Kurzbeschreibung erwähnt, lassen sich mit Ciammo mehrere WGs unabhängig voneinander verwalten. Dabei ist jedoch zu beachten, dass ein Nutzer zur gleichen Zeit immer nur in einer WG als Mitglied sein kann.

Bei jedem Programmstart werden die WGs und Nutzer aus zwei Json-Dateien geladen und beim Beenden des Programms in diesen wieder gespeichert. Zum einfacheren Testen haben wir einige WGs und Nutzer erstellt. Im Folgenden befinden sich die dazugehörigen Zugangsdaten.

| Nr | Benutzername | Passwort | WG-ID | WG-Passwort |
|:--:|:------------:|:--------:|:-----:|:-----------:|
|  1 |    peter24   |   %Pt-1  |  100  |    &_WG-1   |
|  2 |     juli     |   %Ju-2  |  100  |    &_WG-1   |
|  3 |    alexxi    |   %Ax-3  |  101  |    &_WG-2   |
|  4 |     günni    |   %Gn-4  |  101  |    &_WG-2   |
|  5 |     fritz    |   %Fr-5  |  101  |    &_WG-2   |
|  6 |    harald    |   %Hr-6  |  102  |    &_WG-3   |
|  7 |     HUGO     |   %Hu-7  |   0   |      -      |

Es gibt allerdings noch ein kleines Problem im Umgang mit den Nutzdaten. Dieses Problem tritt auf, wenn beispielsweise das Passwort eines Users, welcher sich in einer WG befindet, geändert wird. In einem solchen Fall wird das Passwort nur in der Nutzer-Datei geändert und nicht in der WG-Datei. Denn in dieser sind nicht nur der Nutzername des jeweiligen WG-Mitglieds gespeichert, sondern auch nochmal alle Daten, inkl. Passwort.

Dieses Problem beeinträchtigt aber nicht die Funktionalität des WG-Managers.

## 4. UML

Die beiden Diagramme haben wir in einer ersten Version bei der Architekturplanung erstellt und am Ende des Projekts noch einmal überarbeitet und auf den aktuellen Stand gebracht.

### Use-Case-Diagramm

![Use-Case-Diagramm](/UML/useCaseDiagram.png?raw=true "Use-Case-Diagramm")

### Klassendiagramm

![Klassendiagramm](/UML/classDiagram.png?raw=true "Klassendiagramm")

## 5. Stellungsnahme

### Architektur

- Abstrakte Klasse

    Die Klasse List ist eine Abstrakte Klasse, von welcher niemals ein Objekt erzeugt werden darf. In dieser Klasse sind einige Methoden bereits vollständig implementiert, teilweise aber auch nur Methodenköpfe deklariert.

- Vererbung

    CleaningList und ExpenseList erben beide von List. Hierbei ist praktisch, dass einige Methoden von List in beiden Klassen gebraucht werden, andere dagegen jeweils individuell implementiert werden können. Außerdem erbt CiammoGUI von Application.

- Packages

    GuiController beinhaltet alle Klassen für das GUI. Im Helper Package befindet sich eine Klasse mit allgemeinen Hilfsmethoden, sowie die Speicherstruktur für Einstellungsparameter (bei uns bisher nur Tag-/Nachtmodus). Das Package Threads steuert Threads, welche bei uns für die persistente Speicherung der Nutzdaten in Json-Dateien verwendet werden. In Users befinden sich Klassen, welche die Nutzerstruktur schaffen und in Wgs die Klassen, welche die WG- Struktur aufbauen.

### Clean Code

Es werden bei Attributen generell keine public Zugriffsrechte vergeben.
Statische Methoden verwenden wir nur an sinnvollen Stellen, wie z.B. bei den Configuration und Utilities Klassen oder den Methoden zum Laden, bzw. speichern der Nutzdaten im User- und WgManager.

Generell ist es so, dass wir versucht haben wenige Getter/Setter Methoden zu verwenden, diese jedoch oft für die persistente Speicherung mit Jackson notwendig sind. Jedoch geben wir an manchen Stellen die echten Referenzen der jeweiligen Objekte heraus. Das ist z. B. bei den Gettern der verschiedenen Listen in der Wg- Klasse der Fall.

### Tests

Für die sieben Tests haben wir mit JUnit Tests im Ordner test die jeweiligen Testklassen erstellt. Dort haben wir die wichtigsten Methoden aus den Klassen auf ihre Funktion getestet (inklusiv Negativ-Tests).

### GUI (JavaFX)

Es wurden 14 Controller Klassen und die dazugehörigen fxml-Dateien erstellt. Dazu wurde von uns der SceneBuilder verwendet. Die fxml-Dateien befinden sich im Ordner resources und die Controller im GuiController Package.

### Logging/Exceptions

Die verwendeten Log-Stufen belaufen sich auf Info, Debug, Error und Fatal.
Info loggt den Programmstart, bzw. das Programmende sowie die wichtigsten Änderungen von Methoden an den Nutzdaten. Die Stufe Debug loggt dagegen meist nur die Methodenaufrufe und ist eigentlich nur für die Fehlerfindung vorhanden. Mit Error loggen wir alle einfachen Fehler, wie z.B. wenn ein Passwort nicht den Anforderungen genügt. Und mit Fatal werden schließlich die schwerwiegenden Fehler geloggt, meist auch wenn kritische Exceptions geworfen werden. Dazu gehört beispielsweise der Fall, dass Nutzdaten nicht eingelesen werden konnten.

Log-Daten werden in der Datei A1.log gespeichert. Die Konfiguration befindet sich in log4j2.xml

### Threads

Mit ThreadSaveData können vier verschiedene Threads erstellt werden. Alle vier Threads behandeln das lesen oder schreiben der Json-Dateien für die persistente Nutzdatensicherung. Grundsätzlich werden beim Programmstart zwei Threads zum einlesen der Daten parallel gestartet. Und auch beim beenden des Programms werden wieder zwei parallel gestattete Threads zum schreiben der Json-Dateien gestartet.

### Streams und Lambda-Funktionen Factories

Für unser Projekt gab es nicht viele sinnvolle Anwendungsmöglichkeiten, um Streams einzusetzen. Wir haben jedoch trotzdem parallellaufende Streams an zwei Stellen verwendet. Zum einen zum alphabetischen Sortieren der ShoppingList und zum anderen beim Filtern nach den Ausgaben eines beliebigen Nutzers in der ExpenseList.
