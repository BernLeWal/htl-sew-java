# Modul 1: Lerne die Grundlagen der Java Programmiersprache kennen
[Module 1 in english](Module1.md)

## 1. Java Grundgerüst

Zeigt das Grundgerüst, Syntax und primitive Datentypen mit Java.

### Grundgerüst einer Java-Datei:
Die einfachste Java-Datei mit dem Dateinamen "Main.java" sieht folgendermaßen aus:
```Java
public class Main
{
    public static void main(String[] args) {
        //weiteren Code hier einfügen
    }
}
```

### Kommentare:
Kommentare sind beliebige Erklärungstexte für den Programmier und enthalten keinen Source-Code, 
d.h. Kommentare werden nicht ausgeführt.

```Java
// Alles nach diesen beiden Zeichen ist Erklärungstext
// (gehört nicht zum Code). 

/* Kann überall zwischen oder hinter dem Code eingefügt werden. */
```

### Deklaration von primitiven Datentypen
Sogenannte primitive Datentypen werden für Variablen verwendet, 
dort können einfache Werte abgespeichert und z.B. für Berechnungen verwendet werden.

```Java
// Es wird ein Platz im Speicher bestimmter Größe 
// (je nach Typ) reserviert.

int i;	// Für ganze Zahlen 
long h;	// Für noch längere ganze Zahlen 
double x;	// Für Kommazahlen 
boolean y;	// Für zwei Zustände (wahr/falsch) 
int h,k,l;	// Es können auch mehrere Datentypen (vom
        	// gleichen Typ) in einer Zeile deklariert 
	        // werden
```

### Blöcke von zusammengehörenden Source-Zeilen
```Java
{
    // Blöcke kann man immer machen ist aber nicht immer // sinnvoll. Ein Anfang gehört immer zum nächsten 
    // Ende, somit kann eines alleine nicht existieren 
    // (führt zu einem Programmfehler). Jede Deklaration 
    // endet mit dem zugehörigen Blockende.
}
```