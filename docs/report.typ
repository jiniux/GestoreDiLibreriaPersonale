#import "template.typ": *

#show: template.with(
  full_name: "Dolorem Sit Amet",
  professor: "Lorem Ipsum", 
  student_number: "123456",
  email: "info@example.com"
)

#show heading.where(level: 1): set heading(numbering: none)

= List of Challenging/Risky Requirements or Tasks

#table(
  columns: (2fr, 1fr, 1fr, 2fr),
  [*Challenging Task*], [*Date task is identified*], [*Date challenge is resolved*], [*Explanation on how the challenge has been managed*],
  [Definizione iniziale dell'architettura del software], [29/05/2025], [30/05/2025], 
  [Utilizzo di un architettura a tre livelli: presentazione, applicazione e dominio.],
  [Disaccoppiamento livello di presentazione dal livello di dominio], [29/05/2025], [30/05/2025], [Definizione di una serie di #smallcaps[DTO] per fornire un'interfaccia semplificata.],
  [Implementazione del meccanismo di filtraggio dei libri], [31/05/2025], [31/05/2025], [Utilizzo del pattern #smallcaps[Composite]],
  [Gestione del meccanismo di _rollback_ all'interno di \ `InMemoryTransactionManager`], [06/05/2025], [06/05/2025], [Utilizzo del pattern #smallcaps[Command] per effettuare il _rollback_ in passi separati e tener traccia fin dove il _rollback_ deve essere effettuato.],
  [Notifica al livello di presentazione degli eventi accaduti a livello di applicazione], [05/06/2025], [06/06/2025], [Implementazione del pattern #smallcaps[Observer] per mezzo della classe `EventBus` usata da `BookService`],
  [Sviluppo di interfaccia utente garantendo la riutilizzabilità dei componenti], [06/06/2025], [07/06/2025], [Utilizzo della piattaforma software applicando il pattern #smallcaps[MVC]],
  [Validazione di form composti da altri form], [07/06/2025], [07/06/2025], [Utilizzo del pattern #smallcaps[Composite] per la validazione ricorsiva dei relativi sottoform attraverso l'interfaccia `Validable`.],
  [Persistenza della libreria virtuale in memoria secondaria], [07/06/2025], [07/06/2025], [_Decorazione_ della preesistente famiglia di classi in memoria con salvataggio su file JSON ad ogni modifica],
  [Sviluppo interfaccia utente per la creazione dei filtri], [08/06/2025], [09/06/2025], [Composizione ricorsiva dei filtri attraverso sottodialoghi.],
  [Disaccoppiamento e centralizzazione delle interazioni tra componenti della GUI], [10/06/2025], [10/06/2025], [Utilizzo dei pattern #smallcaps[Mediator] e #smallcaps[Observer]],
  [Semplificazione del cambio di caso d'uso all'interno della barra di ricerca (decidere se usare i filtri oppure testo semplice)], [13/06/2025], [13/06/2025], [Utilizzo del pattern #smallcaps[Strategy] attraverso `SearchStrategy`],
  [Cattura delle eccezioni di dominio e loro traduzione in messaggi di errori significativi all'utente], [13/06/2025], [15/06/2025], [Riutilizzo del #smallcaps[Chain of resposibility] già predisposto per la cattura di eccezione non gestite.]
)

#outline(title: "Sommario", indent: auto)

#show heading.where(level: 1): set heading(numbering: "A.")


= Stato dell'arte

La seguente analisi presenta i principali risultati emersi dalla ricerca sullo stato dell'arte dei software dedicati alla gestione di librerie personali. La ricerca si è focalizzata principalmente sui prodotti _software_ per ambienti _desktop_, con un breve riferimento anche ai servizi basati _server_-centrici (locali o su _cloud_), al fine di fornire una panoramica completa delle soluzioni disponibili.

== Calibre 


Nel panorama _software_ attuale, il principale punto di riferimento per la gestione di una libreria personale, sia in formato digitale che cartaceo, è rappresentato da *Calibre* @calibre. Rilasciato sotto licenza GPLv3, Calibre è un _software_ libero sviluppato principalmente per la gestione di librerie di _e-book_ e per facilitare la loro conversione nei diversi formati supportati dai principali _e-reader_. Tra le sue funzionalità più rilevanti si segnalano:

- *Inserimento e gestione dei libri*: è possibile aggiungere libri alla libreria a partire da un file presente sul proprio computer, oppure inserirli manualmente nel caso in cui il file non sia disponibile (fornendo ISBN oppure i metadati originali). L'utente può modificare i metadati associati a ciascun libro, come titolo, autore, descrizione, parole chiave (tag), ecc. e assegnare una valutazione personale ad ogni libro. È possibile visualizzare tutti i libri attraverso un'interfaccia tabellare o a griglia e se necessario rimuoverli con un apposito pulsante.

- *Visualizzazione del contenuto*: se il libro è associato a un file, è possibile visualizzarne direttamente il contenuto tramite un'apposita GUI.

- *Funzioni di ricerca avanzata*: il sistema di ricerca consente di filtrare e individuare rapidamente i libri presenti nella libreria per mezzo di una sintassi semplice e ben documentata.

- *Conversione e modifica degli e-book*: Calibre permette di convertire i libri digitali in numerosi formati di _e-book_ e offre strumenti per modificare il loro contenuto.

- *Integrazione con dispositivi e-reader*: una volta collegato un _e-reader_ al computer, Calibre è in grado di riconoscerlo automaticamente e fornisce la possibilità all'utente di caricare un libro selezionato sul dispositivo.

- *Web Server*: Calibre dà la possibilità di esporre un _web server_ per permettere la fruizione della propria libreria da un browser web.  

- _*Plug-ins*_: è possibile installare componenti aggiuntivi sviluppati da utenti della comunità che aggiungono funzionalità a Calibre.

A completamento delle funzionalità descritte, Calibre offre funzionalità aggiuntive come la possibilità di *personalizzare l'interfaccia* attraverso fogli di stile CSS, di *effettuare ricerche di libri su numerosi siti web* direttamente dall'interfaccia del programma e una funzione per scaricare e convertire in _e-book_ le notizie presenti sulle principali testate giornalistiche di tutto il mondo.

Attualmente, Calibre risulta essere la soluzione migliore per gli utenti che necessitano di catalogare i propri libri digitali e non: è un prodotto _software_ conveniente (è gratuito senza compromettere la privacy dell'utente finale) funzionale, _cross-platform_ e maturo (è in continuo sviluppo da circa 20 anni). Gli unici svantaggi possono essere la macchinosità di alcune funzionalità per gli utenti inesperti e la poca cura nei confronti dell'esperienza utente. 

Infine, Calibre può essere insufficiente per la gestione della libreria di un'organizzazione più ampia come librerie fisiche, università, ecc. 

== Alfa eBooks Manager

Un'alternativa a Calibre è *Alfa eBooks Manager* @alfaebooks è un _software_ con licenza proprietaria sviluppato da Alfa.NetSoft. È disponibile solo per il sistema operativo Windows e offre una vasta collezione di funzionalità, la cui maggior parte è già presente in Calibre. 

La differenza principale rispetto a Calibre risiede nella maggiore attenzione all'usabilità, specie da parte di utenti meno esperti. Questo è reso possibile da un'interfaccia utente più intuitiva e visivamente più accattivante, sia nella versione _client desktop_ che nella pagina web servita dal _web server_ integrato.

Alcune funzionalità presenti in Alfa eBooks Manager e non in Calibre sono:

- *Supporto nativo di OPDS* (Open Publication Distribution System): protocollo aperto e standardizzato per condividere libri digitali online. Grazie a OPDS, è possibile sfogliare, scaricare e leggere _e-book_ attraverso qualsiasi applicazioni o dispositivo che lo supporti.

- *Rimozione dei duplicati*: utile agli utenti meno esperti per mantenere la libreria ordinata, ridurre il disordine e ottimizzare lo spazio di archiviazione. 

- *Gestione degli autori più dettagliata*: Alfa eBooks Manager permette di compilare una scheda autore con informazioni aggiuntive come data di nascita, una breve biografia, un link esterno e un'immagine.

- *Gestione e riproduzione di audiolibri*: è possibile creare una libreria di audiolibri a partire da un file MP3 oppure M4B. Il software fornisce inoltre un _player_ integrato per riprodurli.

- *Importazione della libreria da vari formati*, come Calibre, Google Books, OPDS, CSV, ...

È bene precisare che molte di queste funzioni possono essere aggiunte anche a Calibre per mezzo di appositi _plug-ins_.

== Booknizer

*Booknizer* @booknizer è un software con licenza proprietario sviluppato da ManiacTools. Come Alfa eBooks Manager, è disponibile solo per Windows. Integra gran parte delle funzionalità presenti sia in Calibre che in Alfa eBooks Manager, ma è più orientato a un utilizzo commerciale (es. biblioteche fisiche) offrendo strumenti specifici per questo contesto, tra cui:

- *Visualizzazione delle statistiche della libreria e generazione di report*: il programma consente di creare grafici che rappresentano diversi aspetti della libreria, come il numero di libri per autore, genere o anno. È anche possibile esportare questi dati sotto forma di report.

- *_Backup_ del _database_*: è possibile effettuare un _backup_ manuale del database oppure programmare _backup_ automatici a intervalli regolari.

- *Importazione dei libri tramite scansione del codice a barre*: i libri possono essere aggiunti alla libreria digitale tramite la scansione del codice a barre, ottenendo automaticamente l'ISBN. La scansione può avvenire usando una webcam o un lettore di codici a barre.

== _Integrated Library System_
Il _software_ utilizzato da organizzazioni come biblioteche, librerie o istituzioni accademiche è comunemente chiamato ILS (_Integrated Library System_), ovvero un sistema integrato per la gestione delle risorse bibliotecarie. Si tratta generalmente di un sistema _software_ distribuito e centrato su _server_, che può essere installato sull'_hardware_ dell'organizzazione oppure offerto come _software-as-a-service_ (SaaS) da un fornitore esterno. In quest'ultimo caso, l'accesso al servizio avviene di norma tramite abbonamento.

Oltre alle funzionalità già offerte dalle soluzioni _desktop_ menzionate, gli ILS forniscono solitamente due interfacce distinte: una rivolta al personale bibliotecario e una destinata agli utenti finali. Quest'ultima consente agli utenti di cercare i titoli disponibili in catalogo attraverso un OPAC (_Online Public Access Catalog_), verificare la disponibilità dei materiali, consultare l'elenco dei libri attualmente in prestito e accedere ad altre funzioni utili. 

Si citano brevemente alcuni _software_ ILS presenti sul mercato attualmente:

- *Koha* @koha: sistema bibliotecario _open source_ completo, distribuito con licenza GPLv3, pensato per un utilizzo a livello enterprise. 

- *Evergreen* @evergreen: ILS _open source_ sotto licenza GPLv2, adatto particolarmente a grandi consorzi bibliotecari e reti di biblioteche per la sua architettura modulare e scalabile.

- *Apollo* @apollo: ILS commerciale progettato per le biblioteche pubbliche. Offre un'interfaccia utente intuitivo e notifiche tramite SMS, e-mail e telefono. 

- *Libib* @libib: ILS basato su _cloud_, che supporta la creazione di diversi tipi di librerie (non solo di libri, ma anche di film, videogiochi, ecc.).

Molti altri sono disponibili sul mercato e forniscono funzionalità aggiuntive (ad esempio, estrema personalizzazione dell'interfaccia) in base alle esigenze delle singole librerie. 

= Raffinamento dei requisiti

== Servizi (con prioritizzazione)

#let service(id, name, importance, complexity, description) = {
  table(
    columns: (auto, 1fr, auto, 1fr),
    [#id ], table.cell(colspan: 3)[#name],
    [*Importanza*], [#importance],
    [*Complessità*], [#complexity]
  )
  block([*#smallcaps("Descrizione")* \ #description], inset: (bottom: 0.5em))
}


#service("S01", "Aggiunta di un libro alla libreria virtuale", "Alta", "Media", [L'utente deve essere in grado aggiungere facilmente e rapidamente un _*libro*_ alla propria libreria virtuale a partire dai seguenti dati (alcuni campi sono _facoltativi_, ossia l'utente può non inserirli):

- *Titolo*.

- *Autori*. L'utente deve almeno specificare un autore per *_libro_*. 

- *Genere*. L'utente deve almeno specificare un genere per *_libro_*.

- *Descrizione* (_facoltativa_).

- *Valutazione* (_facoltativa_) da un valore _intero_ compreso tra 1 e 5 (estremi inclusi).

- *Stato di lettura* (_facoltativo_). L'utente deve poter specificare uno stato di lettura tra "letto", "in lettura", "non letto", "abbandonato". 

Non possono esistere due *_libri_* che hanno lo stesso nome e gli stessi autori: se nella libreria è già presente un _*libro*_ con pari titolo e autori, l'aggiunta sarà rifiutata dal sistema. 

Un libro deve avere almeno una  *_edizione_* associata: l'utente deve inserire almeno un'edizione durante la fase di aggiunta di un libro. Ogni  *_edizione_* è descritta dai seguenti attributi:

- *ISBN*: codice univoco associato ad ogni _*edizione*_. L'utente deve poter inserire sia L'ISBN-10 sia L'ISBN-13. L'utente deve essere in grado di inserire l'ISBN specificando le cifre da cui è composto (i caratteri che non sono cifre possono essere ignorate). Nel sistema non possono esistere due edizioni con lo stesso ISBN, anche se di libri diversi. In aggiunta, è necessario che il formato dell'ISBN sia valido (i.e. la sua lunghezza in cifre) e che la sua integrità sia verificata per mezzo delle _check digits_.

- *Autori aggiuntivi* dell'_*edizione*_ (_facoltativo_).

- *Numero edizione* (_facoltativo_).

- *Titolo edizione* (_facoltativo_).

- *Editore*. L'utente deve specificare uno e un solo *_editore_* per _*edizione*_.

- *Formato* (_facoltativo_).

- *Lingua* (_facoltativa_).

- *Anno di pubblicazione* (_facoltativa_). 
])

#pagebreak(weak: true)

#service("S02", "Rimozione di un libro dalla libreria virtuale", "Alta", "Bassa")[L'utente deve essere in grado di eliminare un *_libro_* dalla libreria. La sua eliminazione deve comportare la rimozione di tutte le sue *_edizioni_* dal sistema. Inoltre, l'utente deve avere la possibilità di rimuovere una *_edizione_* da un libro. Se un *_libro_* ha una *_edizione_*, la rimozione di quest'ultima deve comportare la rimozione del _*libro*_ dalla libreria. 
]

#service("S03", "Modifica informazioni di un libro dalla libreria virtuale", "Alta", "Media")[
L'utente deve essere in grado di modificare le informazioni relative al *_libro_* e/o alle sue edizioni già espresse in S01. Ogni modifica deve soddisfare i vincoli specificati in S01.
]


#service("S04", "Visualizzazione dei libri presenti nella libreria virtuale", "Alta", "Media")[
  I *_libri_* devono essere presentati all'utente in una vista d'insieme, che può essere visualizzata in formato *tabella*. L'utente deve poter scegliere liberamente tra le due modalità di visualizzazione. Se non è possibile visualizzare in un'unica vista tutti libri presenti nella libreria virtuale, è necessario ricorrere alla paginazione. 

  In entrambe le viste, per ciascun *_libro_* devono essere mostrati almeno i seguenti elementi: *titolo*, *autori*, *stato di lettura* e *valutazione*. 

  L'utente deve poter ordinare la visualizzazione dei libri nei seguenti modi:

  - rispetto al titolo, in ordine alfabetico.
  - rispetto all'anno di pubblicazione.
]

#service("S05", "Ricerca di uno o più libri nella libreria virtuale", "Media", "Alta")[
Il sistema deve fornire all'utente una funzione di ricerca per trovare più rapidamente i *_libri_* a partire dai loro attributi (*_edizioni_* incluse).

L'utente deve poter filtrare la ricerca per mezzo di filtri basati sul confronto tra gli attributi dei libri (titolo, autore, genere, anno di pubblicazione, ecc.) e valori di riferimento specificati. Il sistema deve supportare diversi tipi di confronto come uguaglianza, contenimento testuale, confronti numerici (maggiore, minore, uguale) e altri operatori pertinenti, laddove applicabili. L'utente deve poter combinare più filtri utilizzando operatori logici binari, come "e", "o".

I risultati della ricerca devono essere presentati nella medesima vista descritta in S04.
]

#service("S06", "Salvataggio della libreria in modo persistente su memoria secondaria", "Alta", "Alta")[
  I dati relativi alla libreria virtuale devono essere salvati in modo persistente su memoria secondaria. Il salvataggio deve avvenire automaticamente a ogni modifica dello stato della libreria.
]


#service("S07", "Modifica rapida della valutazione e dello stato di lettura del libro", "Bassa", "Media")[
 L'utente deve poter modificare rapidamente la valutazione e lo stato di lettura di un libro, senza dover necessariamente accedere alla sezione dedicata alla modifica dei dettagli del libro. Idealmente, queste modifiche dovrebbero essere eseguibili direttamente dalla schermata descritta in S04.
]

#service("S08 ", "Ricerca rapida di libri a partire da testo", "Media", "Bassa")[
  L'utente deve poter ricercare i libri in maniera rapida attraverso un'apposita _barra di ricerca_, senza la necessità di creare/comporre filtri. Il sistema deve includere tra i risultati della ricerca i libri che contengono il testo nella barra di ricerca negli attributi "titolo", "autori" del libro e negli attributi "ISBN", "editore", "titolo edizione" di ogni sua edizione.
]

#pagebreak(weak: true)

== Requisiti non funzionali 

#let nfr(id, name, description) = {
  table(
    columns: (auto, 1fr, auto, 1fr),
    [#id ], table.cell(colspan: 3)[#name]
  )
  block([*#smallcaps("Descrizione")* \ #description], inset: (bottom: 0.5em))
}

#nfr("NFR01", [Piattaforma _desktop_])[
  L'applicazione deve essere sviluppata come applicazione _desktop_ autonoma.
]

#nfr("NFR02", [Interfaccia utente grafica])[
  L'applicazione deve fornire un'interfaccia utente grafica (GUI).
]

#nfr("NFR03", [Usabilità e efficienza dell'interfaccia utente])[
  L'interfaccia utente del sistema deve garantire un equilibrio ottimale tra semplicità d'uso per utenti inesperti ed efficienza operativa.  Di conseguenza: 

  - Tutti i servizi devono essere accessibili tramite un'interfaccia grafica che adotti convenzioni standard e familiari agli utenti, utilizzando elementi di controllo comuni come pulsanti, campi di testo, menù a tendina e altri componenti dell'interfaccia universalmente riconosciuti.

  - L'interfaccia deve mantenere coerenza nell'utilizzo di icone, disposizione degli elementi e modalità di interazione, al fine di ridurre la curva di apprendimento e aumentare l'efficienza d'uso.

  - L'interfaccia utente deve essere progettata per essere il più possibile interattiva: ogni interazione — come il clic su un pulsante, l'inserimento di testo in un campo, ecc. — deve generare un _feedback_ visivo chiaro che indichi che il sistema ha recepito l'input e sta elaborando o ha completato l'operazione richiesta.

  - L'interfaccia utente deve prevenire errori comuni e offrire messaggi chiari e utili in caso di errore.
]

#nfr("NFR04", "Portabilità")[
  Il sistema deve essere facilmente portabile tra diversi sistemi operativi senza particolare sforzo da parte dell'utente.
]

#nfr("NFR05", "Scalabilità rispetto alla dimensione della libreria virtuale")[
  L'utente deve essere in grado di immagazzinare un numero arbitrario di *_libri_* all'interno della propria libreria senza avere cali di prestazioni significativi. 
]


== Scenari d'uso dettagliati

#let scenario(id, name, use_case, condition, warranty, type, description) = {
  table(
    columns: (auto, 1fr, auto, 3fr),
    [#id ], table.cell(colspan: 3)[#name],
    table.cell(colspan: 3)[Caso d'uso], [#use_case],
    table.cell(colspan: 3)[Tipo], [#type],
  )
  block[*#smallcaps("Condizione")* \ #condition] 
  block[*#smallcaps("Garanzia")* \ #warranty] 
  block[*#smallcaps("Passi")* \ #description]
}

#scenario("SC01", "Aggiunta minimale di un libro con successo", "Aggiunta di un libro alla libreria virtuale", "Nessuna.", "Il libro è inserito all'interno della libreria virtuale.",  "Principale")[
  1. L'utente preme sul pulsante "Aggiungi libro".
  2. Il sistema mostra all'utente la schermata di aggiunta del libro.
  3. L'utente inserisce tutte i dati obbligatori  e relativi al libro (titolo, autori, genere) rispettando i vincoli in S01.
  4. L'utente aggiunge una nuova edizione del libro, specificando i dati richiesti (ISBN ed editore) e rispettando i vincoli in S01.

  5. L'utente conferma l'inserimento premendo il pulsante "Aggiungi".
]


#scenario("SC02", "Modifica di un libro con successo", "Modifica di un libro", "Nessuna.", "Le modifiche sul libro specificate dall'utente sono applicate in maniera permanente.", "Principale")[
  1. L'utente #underline[accede alla propria libreria virtuale], seleziona un libro e clicca sul pulsante "Modifica libro". 
  2. Il sistema mostra all'utente la schermata di modifica del libro, riempiendo i campi con i dati attuali del libro.
  3. L'utente modifica gli attributi e/o le edizioni del libro rispettando i vincoli posti da S01.
  4. L'utente conferma le modifica premendo sul pulsante "Salva Libro".
]

#scenario("SC03", "Rimozione di un libro con successo", "Modifica di un libro", [
  Nessuna
], "Il libro è rimosso dalla libreria virtuale.", "Secondaria")[
  3a. L'utente clicca sul pulsante "Rimuovi".
  
  4a. Il sistema chiede all'utente se è sicuro di rimuovere il libro.

  5a. L'utente conferma la sua intensione premendo sul pulsante "Conferma"
]


#scenario("SC04", "Ricerca con nome", "Ricerca di uno o più libri nella libreria virtuale", "Nessuna.", "Lo stato della libreria rimane invariato.", "Principale")[
  1. L'utente preme sulla barra di ricerca.

  2. Il sistema evidenza la barra di ricerca per indicare all'utente che sta attivamente ricevendo _input_ da tastiera.
  3. L'utente scrive il nome del libro o dei libri che vuole cercare.
  4. L'utente preme il pulsante "Cerca" o preme il tasto "Invio" sulla tastiera.
  5. L'utente #underline[visualizza i libri] che contengono il testo nella barra di ricerca negli attributi "titolo", "autori" del libro e negli attributi "editore", "titolo edizione", "numero edizione" di ogni sua edizione.
]

#scenario("SC05", "Ricerca con filtri", "Ricerca di uno o più libri nella libreria virtuale", "Nessuna", "Lo stato della libreria rimane invariato.", "Secondario")[
  1a. L'utente preme sul buttone "Filtra", accanto alla barra di ricerca.
    1. Il sistema espone la lista dei filtri utilizzati, con accanto i pulsanti "Aggiungi filtro" e "Rimuovi filtro".

    2. L'utente preme su "Aggiungi filtro".

    3. Il sistema visualizza la schermata di creazione di un filtro.

    4. L'utente specifica il tipo di operatore di confronto e il valore di riferimento da utilizzare e preme sul pulsante "Aggiungi".

    5. Il sistema aggiorna la lista di filtri.

  2a. Il sistema non evidenza la barra di ricerca.

  3a. L'utente non inserisce alcun testo nella barra di ricerca.

  5a. L'utente #underline[visualizza i libri] che rispettano i filtri specificati dall'utente.
]

#scenario("SC06", "Visualizzazione tabellare dei libri", "Visualizzazione dei libri presenti nella libreria virtuale", "L'utente si deve trovare nella schermata di visualizzione dei libri.", "Lo stato della libreria rimane invariato.", "Principale")[
  1. L'utente visualizza i libri in formato tabellare. 
]

#scenario("SC07", "Visualizzazione dei libri con ordinamento", "Visualizzazione dei libri presenti nella libreria virtuale", "L'utente si deve trovare nella schermata di visualizzione dei libri.", "Lo stato della libreria rimane invariato.", "Secondario")[
1a. L'utente imposta l'ordinamento in base alla data di pubblicazione dell'edizione del libro attraverso un opportuno elemento grafico (ad esempio un menù a tendina).
  1. Il sistema, per ciascun libro, individua l'edizione con la data di pubblicazione più vecchia e utilizza tale data come riferimento per l'ordinamento. Se non è presente, il libro non viene incluso nella ricerca.

  2. L'utente visualizza l'elenco dei libri nel formato scelto, ordinato in modo ascendente secondo il criterio selezionato.
]


#scenario("SC08", "Visualizzazione dei libri con ordinamento", "Visualizzazione dei libri presenti nella libreria virtuale", "L'utente si deve trovare nella schermata di visualizzione dei libri.", "Lo stato della libreria rimane invariato.", "Secondario")[
1b. L'utente seleziona una modalità di visualizzazione tra quelle disponibili utilizzando un apposito elemento dell'interfaccia grafica (ad esempio, un _radio button_) e, tramite un componente grafico adiacente, imposta l'ordinamento alfabetico basato sul titolo del libro.
  1. Il sistema presenta l'elenco dei libri nel formato selezionato, ordinandoli in ordine alfabetico crescente in base al titolo.
]

== Requisiti esclusi

Sono stati esclusi dal progetto i seguenti requisiti:

- *Supporto per _e-book_ ed _e-reader_* (_servizio_): il requisito è spesso fornito dai principali software simili già presenti sul mercato, ma è oltre gli scopi del progetto. Inoltre l'integrazione con gli _e-reader_ richiederebbe l'interazione con funzionalità di basso livello, che aggiungono il rischio di introdurre vulnerabilità.

- *Accesso alla libreria tramite _web server_* (_servizio_): nonostante sia spesso fornito da software analoghi, non è un requisito fortemente richiesto dai software desktop data l'esistenza di prodotto _server_-centrici molto più maturi, sicuri e funzionali. È dunque escluso dagli scopi del progetto.

- *Accesso alla libreria tramite OPDS* (_servizio_): richiede l'esposizione di un _server_ HTTP. È oltre gli scopi del progetto.

- *Importazione e riproduzione _file_ audio per audiolibri* (_servizio_): richiede l'implementazione di un _player_ integrato, il quale è oltre agli scopi del progetto e potrebbe richiedere il pagamento di licenze per il supporto di alcuni _codec_.

- *Supporto a piattaforme _mobile_* (_requisito non funzionale_): potenzialmente più richiesto data il maggior utilizzo degli _smartphone_ rispetto ai canonici computer, ma oltre agli scopi del progetto.

- *Autocompilazione degli attributi del libro e dell'edizione a partire da ISBN*: potenzialmente utile e più efficiente rispetto all'inserimento manuale dei dati, ma oltre gli scopi del progetto.

- *Supporto a _plug-ins_*: potenzialmente vantaggioso per favorire la crescità di una comunità di appassionati attorno al software, ma richiede l'attento studio di una API e di un servizio di distribuzione dedicato al fine di garantire la sicurezza dell'utente a fronte di _plug-ins_ malevoli. Ciò è oltre gli scopi del progetto. 

== Assunzioni 

Nella specifica dei servizio si è assunto che:

- *Non esistono libri con lo stesso titolo e gli stessi autori*: i casi in cui ciò avviene sono quasi nulli, per cui rilassare questo vincolo complicherebbe il sistema senza alcun beneficio.  

- *Un libro non è la sua edizione*: si assume che un libro sia l'entità astratta del testo creato dall'autore, mentre l'edizione è una sua realizzazione concreta.

- *L'identificazione univoca degli autori non è considerata rilevante*: sebbene esista la rarissima possibilità che più autori abbiano lo stesso nome, distinguere tra loro tramite identificatori specifici (come ISNI o ORCID) complicherebbe inutilmente il sistema. Inoltre, richiedere all'utente l'inserimento di tali dati, spesso non facilmente reperibili, potrebbe generare frustrazione e ridurre l'efficienza nell'utilizzo del software.

- *Il _software_ non viene essere usato in ambito commerciale*: in gran parte degli ambiti commerciali non si utilizzano _software_ indipendenti per gestire una libreria, ma _software_ _server_-centrici. Di conseguenza sono stati ignorati requisiti puramente legati all'ambito commerciale come il supporto a lettori di codice a barre o funzioanlità legate al tracciamento dei _noleggi_ o _prestiti_. Si assume quindi che l'utente finale è un utente comune _consumatore_ e non un _commerciante_, per cui attributi del libro come "valutazione" e "stato lettura" hanno senso.

Infine, *si assume che tutte i requisiti esclusi possano essere implementati in versioni successive del _software_*, di conseguenza l'architettura del _software_ è studiata al fine di rendere possibili tali aggiunte senza il rischio di dover riscrivere parte del codice.

== _Use case diagrams_

#figure[
  #align(center)[#image("uml/use_case.png")] 
]



= Architettura software

Il software è suddiviso in tre diversi livelli:

- Livello di *presentazione*: implementa le funzionalità necessarie ad offrire l'utente un interfaccia con cui interagire con il software. Dipende dal sottostante livello di applicazione.

- Livello di *applicazione*: _realizza_ i casi d'uso coordinando gli oggetti di dominio. Difatti, dipende dal sottostante livello di dominio.

- Livello di *dominio*: contiene entità, regole e servizi legate al dominio applicativo. Non dipende dal alcun livello. 

In aggiunta è anche presente un livello di *infrastruttura*, il quale implementa le tecnologie richieste dal livello di dominio e di applicazione per portare a termire le loro funzionalità. In tal modo, questi due livelli possono evolvere in maniera indipendente rispetto alla tecnologia utilizzata (si potrebbe considerare un'applicazione del _design pattern_ #smallcaps[Bridge] a livello architetturale).

Talvolta, ogni livello può fare affidamento ad una *libreria di classi di utilità* che contiene logica condivisa o classi di generico utilizzo. 

== Visione statica del sistema: _Component Diagram_

Di seguito è presentato un _Component Diagram_ che descrive genericamente l'architettura del software.

#figure[
  #image("uml/component_diagram.png")
] 

== Visione dinamica del sistema: _Sequence Diagram_

Di seguito è presentato un _Sequence Diagram_ che dimostra le interazioni tra i sottosistemi dapprima durante l'avvio dell'applicazione dopo durante SC01. Dato il sostanzioso numeri di oggetti coinvolti e per evitare di sovraccaricare il diagramma, sono solo mostrate le interazioni con le loro corrispettive rappresentazioni di alto livello. Il resto degli scenari segue un flusso simile: nella maggior parte degli scenari, il flusso di esecuzione passa per tutti e tre i livelli. 

#figure[
  #image("uml/sq_01.png")
]

#figure(caption: [_Sequence Diagram_ (Crea libro)])[
  #image("uml/sq_02.png", width: 440pt)
]


= Dati e loro modellazione

Nonostante nel progetto non sia esplicito l'utilizzo di un DBMS, risulta particolarmente rilevante mostrare le relazioni che intercorrono tra gli oggetti di dominio, le quali sono mostrate nel _class diagram_ che segue. 

#figure(caption: [_Class diagram_ degli oggetti di dominio])[
  #image("uml/book_class_diagram.png")
]

È importante precisare che gli attributi di tipo `string` identificano stringhe non vuote, ossia stringhe che non sono costituite da soli spazi o caratteri simili. Benché nel codice molti dei campi siano descritti da appositi _oggetti valore_ al fine di garantire la loro integrità, si è deciso di inserire nel _class diagram_ solo il loro tipo primitivo per evidenziare meglio il tipo di dato. Si presuppone che per ogni campo sia presente un getter e un setter (solo se questo non è marcato come `read-only`).

= Scelte progettuali

Di seguito sono presentate le cinque scelte più importanti prese durante la progettazione del software:

1. *Isolamento del dominio*: gli oggetti di dominio rappresentano la _source-of-truth_ del sistema, per cui isolarli rispetto al resto del sistema è fondamentale per garantire che i dati e le regole al suo interno non vengano influenzati da dettagli tecnologici. Tale isolamento inoltre aiuta a localizzare la conoscenza di dominio all'interno di un punto preciso del software e rende più semplice la scrittura dei test di unità.

2. *Utilizzo dei DTO per disaccoppiare il livello di presentazione dagli oggetti di dominio*. Il livello di applicazione fornisce una interfaccia più semplice per invocare i casi d'uso, per cui si comporta come un #smallcaps[Facade] nei confronti del dominio e dell'infrastruttura. Talvolta è però necessario passare trasferire verso o dal livello di applicazione gli oggetti di dominio: a tale scopo si utilizza il _design pattern_ del #smallcaps[Data Transfer Object]. I DTO sono rappresentati da oggetti POJO, al cui interno contengono unicamente dati e metodi per semplificare la loro creazione. Quando vengono ricevuti da un servizio del livello di applicazione i DTO sono convertiti in oggetti di dominio per mezzo di un apposita classe di mappatura, che può implementare controlli triviali al fine di garantire o meno la conversione. I DTO sono prettamente utilizzati per semplificare il livello di presentazione (non deve preoccuparsi della loro composizione più o meno complessa) e per isolare l'utilizzo degli oggetti di dominio ai soli casi d'uso. L'utilizzo dei DTO consentirebbe anche di sostituire facilmente un livello di presentazione nativo con unp basato su tecnologie _web_ dal momento che i DTO sono facilmente modificabili per essere serializzati in JSON, XML ecc.

3. *Utilizzo del pattern #smallcaps[Composite] per l'implementazione dei filtri*. Il pattern #smallcaps[Composite] è conseguenza naturale del meccanismo di filtri descritti in S05. Il #smallcaps[Composite] dà la possibilità di creare filtri molto complessi in maniera semplice, elegante e facilmente estendibile. Anche l'interfaccia utente ne beneficia: la finestra di composizione del filtro offre due opzioni — aggiungere un oggetto foglia oppure un oggetto composito. Nel caso della scelta di un oggetto composito, la medesima finestra viena riaperta in modo ricorsivo e il filtro è composto man mano che le finestre "più in profondità nella ricorsione" vengono chiuse.


4. *Uso di #smallcaps[Strategy] e #smallcaps[Facade] per astrarre l'infrastruttura di persistenza*. L'infrastruttura legata alla persistenza è astratta all'interno del livello di dominio attraverso interfacce con suffisso _Repository_ (es. _BookRepository_). Tale interfaccia è paragonabile al _design pattern_ #smallcaps[Strategy] poiché permette l'implementazione di più _algoritmi_ per garantire la persistenza degli oggetti di dominio. Tale pattern rende inoltre più semplice la creazione di una strategia per la gestione di una collezione _in memory_ ai fini del testing. D'altra parte, un'interfaccia _Repository_ rappresenta anche un #smallcaps[Facade]: infatti espone un'interfaccia di alto livello che esula i client dalla complessità intrinseca legata alla strategia di persistenza utilizzata (ad esempio, un _embedded database_ come SQLite). Purtroppo però bisogna puntualizzare che aggiungere nuovi elementi all'interfaccia _Repository_ potrebbe risultare difficile, poiché comporta la modifica di tutte le classi che implementano tale interfaccia. Questo potrebbe risultare poco flessibile in progetti di grandi dimesioni. 


5. *Uso di MVC per la gestione della GUI*. Per la gestione dell'interfaccia utente si adotta il pattern #smallcaps[Model-View-Controller], che consente di isolare le responsabilità tra logica di presentazione, logica di controllo e modello dati. Ciò permette di evitare la formazione di un legame permanente tra modello (in questo caso i servizi del livello di applicazione e i DTO) e la visualizzazione (i componenti grafici descritti tramite linguaggio dichiarativo o imperativo), in modo tale da consentire che i due possano evolvere separatamente. Ciò è reso possibile grazie alla presenza di un oggetto denominato _controllore_ che funge da intermediario tra visualizzazione e modello attraverso l'utilizzo dei pattern #smallcaps[Mediator], #smallcaps[Observer] e #smallcaps[Command].  


#figure(caption: [_Package diagram_ che riassume le principali dipendenze. Si noti che il dominio non dipende da nessun altro _package_ se non eventualmente ed esclusivamente dal _package_ di classi di utilità.])[
  #image("uml/package_diagram.png", width: 260pt)
]

#figure(caption: [_Class diagram_ che rappresenta l'utilizzo dei DTO da parte del BookService])[
  #image("uml/book_dto_class_diagram.png", width: 350pt)
]

#figure(caption: [_Class diagram_ della gerarchia di classi parziale di `Filter<T>`])[
  #image("uml/partial_filter_class_diagram.png")
]


#figure(caption: [Esempio di utilizzo delle interfacce _Repository_. Si noti che questo mantiene l'isolamento del dominio])[
  #image("uml/repository_diagram.png")
]

#figure(caption: [_Sequence diagram_ che dimostra l'interazione tra Model (`BookService`, `BookDto`), View (`Combobox`) e Controller (`BookViewController`).])[
  #image("uml/sq_03.png")
]

= Progettazione di basso livello 

Di seguito sono presentate le scelte di progettazione di basso livello più rilevanti.

== Filtri

Come già accennato in precedenza, al fine di implementare la funzionalità della ricerca per filtro è stato utilizzato il pattern #smallcaps[Composite]. Nel caso dei filtri, il `Component` è rappresentato dall'interfaccia `Filter<T>` mentre il `Composite` da `CompositeFilter<T>`.

#figure[
  #image("uml/filter_n_composite_filter_class_diagram.png",width: 350pt)
]

L'operazione `apply` restituisce vero se e solo se l'oggetto `t` verifica la condizione del filtro, ossia può essere incluso nel risultato. L'unica classe ad estendere l'`AbstractCompositeFilter` è `BinaryOperatorCompositeFilter`, il quale implementa la concatenazione di $n$ operazioni binarie per mezzo di un operatore $and$ o $or$. 

#figure[

#image("uml/binary_operator_composite_filter_cd.png", width: 420pt)
]
In questo caso è stato applicato il pattern #smallcaps[Strategy] per evitare la creazione di due sottoclassi distinte che si sarebbero differenziate unicamente per l'operatore utilizzato. L'astrazione è rappresentata dalla classe `BinaryOperatorCompositeFilter`, che implementa l'algoritmo principale, mentre `BinaryOperator` costituisce l'implementazione e definisce l'operatore da applicare.
Al posto delle interfacce, è stata utilizzate un'enumerazione, in quanto in Java le enumerazioni sono oggetti a tutti gli effetti e possono quindi esporre operazioni. Inoltre, poiché non necessitano di parametri, possono essere implementati secondo il pattern #smallcaps[Singleton], evitando l'istanziazione non necessaria di oggetti aggiuntivi.

`Filter<T>` è implementato da numerosi filtri "foglia", mostrati nel seguente _class diagram_.

#figure[
  #image("uml/filter_leaf_cd.png")
]

Si noti che anche `CompareFilter` utilizza il pattern #smallcaps[Strategy] per evitare la proliferazione di sottoclassi. Tuttavia il requisito funzionale prevede che un libro sia filtrato a partire dai suoi campi, ma non è presente alcuna classe in grado di fare ciò. Questa limitazione ha portato a realizzare l'#smallcaps[Adapter] `BookFilter`, in modo tale che le classi di filtro già presenti possano essere riutilizzate anche per filtrare i libri.

#figure[
  #image("uml/book_filter_cd.png", width: 350pt)
]

Come si può osservare, la classe `BookFilter` adatta l'interfaccia `Filter<T>` per renderla applicabile agli oggetti di tipo Book. Questo adattamento è reso possibile dalla proprietà `field`, che estrae l'attributo del libro in modo che sia applicato al filtro `filter`. La compatibilità tra il filtro e il tipo del campo è verificata a _runtime_ tramite il metodo supportsReferenceClass.
Poiché molte varianti dell'enumerazione `BookFilterField` si riferiscono a proprietà che possono contenere più valori (ad esempio, nel caso di libri con più edizioni), ciascuna variante fornisce un #smallcaps[Iterator] che consente di scorrere tali valori. `BookFilterField` espone un #smallcaps[Factory Method] per la creazione degli #smallcaps[Iterator] e agisce come uno #smallcaps[Strategy] nell'esporre il metodo che si occupa della verifica del tipo di classe.

L'uso delle enumerazioni al posto di classi indipendenti è motivato dal fatto che esse rendono espliciti i tipi di filtro supportati dal sistema e dalla loro più semplice ispezionabilità da parte di eventuali consumatori (ad esempio, una _Repository_ che genera query ad hoc), senza dover ricorrere a tecniche come la reflection o l'uso di `instanceof`.

== Persistenza

Il livello di persistenza consiste di implementazioni delle classi _Repository_ e di altre classi di supporto utili al livello di applicazione, come la gestione delle transazioni per garantire l'atomicità di un insieme di operazioni. Poiché queste classi dipendendono direttamente da classi della stessa famiglia, è necessario che siano _create insieme_. La classe `DataAccessProvider` agisce come una #smallcaps[Abstract Factory] poiché permette di scegliere tra più tecnologie di persistenza e garantisce la coerenza nell'utilizzo delle classi concrete di quella sola tecnologia. Oltre ai metodi _factory_, `DataAccessProvider` espone l'operazione `gracefullyClose` per permettere che le eventuali risorse utilizzate ai fini di persistenza siano rilasciate e che ogni le transazione in sospeso sia stata conclusa.

#figure(caption: [_Class Diagram_ delle classi create da `DataAccessProvider`. Le operazioni di `BookRepository` sono state emesse poiché eccessive e oltre lo scopo del diagramma.])[
  #image("uml/data_access_provider_cd.png")
]

Il `TransactionManager` adotta il pattern #smallcaps[Command] per rinviare l'esecuzione della transazione fino a quando il contesto necessario non è stato correttamente inizializzato. In questo modo, eventuali errori generati durante l'esecuzione di quest'ultima possono essere intercettati, in modo tale che il sistema garantisca il _rollback_ delle modifiche effettuate fino a quel momento. Oltre a `Transaction` (realizzazione pura del pattern) è presente anche `TransactionWithResult`, che rappresenta una variante del medesimo pattern affinché supporti la restituzione di un risultato ottenuto durante una transazione.  

Ai fini di estendibilità e riutilizzo dei moduli software in più contesti, si presuppone che sia le classi `TransactionManager` sia tutte le classi _Repository_ garantiscano la _thread-safety_. 

=== Persistenza _in-memory_

Nonostante non si tratti di vera e propria persistenza in memoria secondaria, questa famiglia di classi consente di simulare la presenza di una collezione persistente attraverso una serie di strutture localizzate in memoria primaria. Ciò torna utile specialmente ai fini di _testing_, ma può essere riutilizzato da altri tipi di tecniche di persistenza (ad esempio, la persistenza su file JSON). 



#figure[
  #image("uml/inmemory_persistency_cd.png", width: 380pt)
]


In questo tipo di persistenza l'atomicità e la _thread-safety_ sono garantite da un _readers-writer lock_ all'interno di `InMemoryTransactionManager`, il quale viene acquisito durante una transazione oppure richiamando manualmente un metodo su una classe _Repository_.  



`InMemoryTransactionManager` mantiene un riferimento di tipo _thread-local_ ad un `TransactionState`. `TransactionState` è una classe il cui solo scopo è mantenere una pila di azioni di ripristino (ossia una serie di #smallcaps[Command]), inserite dalle _Repository_ per mezzo del metodo `addRollbackAction`. È stata definita come _inner class_ statica di `InMemoryTransactionManager`, affinché il suo metodo `rollback()` non sia esposto all'esterno (e quindi richiamabile solo dal gestore delle transazioni). Infatti, nel caso in cui si verifica un'eccezione durante l'esecuzione di una `Transaction`, `InMemoryTransactionManager` si preoccuperà di richiamare il metodo `rollback` al fine si eseguire le operazioni le `RollbackAction` secondo una politica LIFO.

Dal diagramma si può notare che `InMemoryDataAccessProvider` non si comporta come una vera e propria #smallcaps[Abstract Factory] poiché non crea gli oggetti ogni qual volta le operazioni per ottenerli sono invocate. Infatti, `InMemoryDataAccessProvider` crea le istanze necessarie all'interno del proprio costruttore e mantiene un riferimento ad esse in modo da restituirle attraverso i relativi metodi. Nonostante si possa rimodulare la progettazione di queste classi in modo da ottenere un'implementazione pura dell'#smallcaps[Abstract Factory], ciò non è necessario poiché comporterebbe la creazione di istanze aggiuntive superflue.

=== Persistenza con file JSON

Questo famiglia di classi di persistenza permette di salvare in memoria secondaria i dati per mezzo di uno o più file JSON siti in una specifica cartella#footnote[Varia in base al sistema operativo.]. Poiché il formato JSON non è ottimizzato per essere utilizzato come base di dati, si è scelto di implementarlo riusando le classi di persistenza _in-memory_. Ciò ottimizza l'accesso in lettura poiché elimina la necessità di riaprire e scorrere per intero il file. D'altra parte, è necessario puntualizzare che il mantenimento della collezione in memoria primaria potrebbe essere problematico nel caso in cui la libreria virtuale diventi molto grande (e risultare in una `OutOfMemoryException`). In tal caso bisognerebbe utilizzare un'implementazione delle classi di persistenza basata su DBMS.

Le classi `JsonTransactionManager` e `JsonBookRepository` sono rassimilabili a dei #smallcaps[Decorator] rispettivamente delle classi `InMemoryTransactionManager` e `InMemoryBookRepository`. `JsonTransactionManager` non aggiunge alcun comportamento aggiuntivo ed è stata aggiunta unicamente per coerenza e in vista di future estensioni. Invece, `JsonBookRepository` utilizza la classe `JsonFile` per salvare il contenuto attuale della collezione su un file JSON dopo ogni modifica (ossia durante `saveBook` e `deleteBook`, subito dopo aver inoltrato la richiesta a `InMemoryBookRepository`) e per caricarlo in fase di inizializzazione.

Durante il salvataggio, tutti gli oggetti di dominio `Book` sono convertiti in `JsonBookData` (un oggetto #smallcaps[DTO]), per mezzo del `JsonBookDataMapper`, un #smallcaps[Singleton].

#figure[
  #image("uml/json_persistence_cd.png")
]

== GUI

Per la realizzazione della GUI è stato impiegata la piattaforma software JavaFX, alternativa più moderna e flessibile rispetto al classico Swing. JavaFX utilizza i file FXML per definire il _layout_ di una _scena_, ossia un insieme di componenti grafici. Ogni scena può essere associata ad un _controllor_ che regola l'interazione tra gli elementi della stessa scena ed espone operazioni per l'utilizzo da parte di altri controllori (solitamente i controllari degli elementi "padri"). Questo permette il riutilizzo dello stesso componente in più contesti e a favorire una maggiore coerenza all'interno dell'interfaccia utente. Di conseguenza, è naturale la scelta del pattern #smallcaps[MVC] durante la progettazione di applicazioni JavaFX. 

=== Gestione delle dipendenze dei _Controller_

Un _controller_ ha talvolta dipendenze con componenti esterni alla scena per fornire i servizi all'utente (ad esempio, deve in qualche modo ottenere `BookService` per poter invocare i casi d'uso). A tale scopo, è stata creata una classe `ServiceLocator` che fornisce l'accesso e talvolta l'inizializzazione di tali dipendenze. Poiché `ServiceLocator` è un #smallcaps[Singleton], è accessibile globalmente a tutti i _controller_. 

Nel codice, i _controller_ accedono all'istanza di `ServiceLocator` solo nel costruttore, prelevano le dipendenze necessarie e salvano i loro riferimenti al proprio interno. In questo modo, è possibile tenere sotto controllo il numero di dipendenze di ogni _controller_ in un modo semplice e più esplicito. 

Infine, `ServiceLocator` è utilizzato al posto di $n$ #smallcaps[Singleton] poiché permette di localizzare la decisione su quali specializzazioni di servizi utilizzare in un punto specifico del codice. La dipendenze `JsonDataAccessProvider` è l'unica che deve essere inizializzata manualmente da un client perché possa essere utilizzata: questo è necessario affinché si possa intercettare l'errore di caricamento del file e mostrare un messaggio di errore opportuno. 

=== Localizzazione

Il software è stato disaccoppiato dalla lingua dei messaggi presenti sull'interfaccia attraverso l'utilizzo di stringhe di localizzazione. Tali stringhe sono usate come chiavi all'interno di file `.properties`, al cui interno ad ogni chiave è associata il messaggio in una determinata lingua. Per isolare la gestione di questi file, è stata creata la classe `Localization` con il compito di caricare e formattare i messaggi. Inoltre, `Localization` espone un interfaccia che attende un valore dell'enumerazione `LocalizationStrings`, in modo tale che il programmatore non commetta errori durante la digitazione del nome all'interno del codice.

=== Creazione di scene e messaggi di errori

Per creare le scene e i messaggi di errore (ossia le cosiddette _message box_) sono state create le rispettive classi `FXMLFactory` e `AlertFactory`. Benché non siano un'istanza dell'#smallcaps[Abstract Factory], espongono una serie di metodi _factory_ per creare e, in caso, assemblare tali oggetti. Come già detto per le altre classi, l'approccio favorisce l'isolamento di tali responsabilità in classi specifiche. 

=== #smallcaps[Chain of Responsibility] per la gestione degli errori

Per gestire un eventuale eccezioni, è stato utilizzato il #smallcaps[Chain of Responsibility] per inoltrare l'errore ad uno o più gestori in cascata, in maniera tale che, ad esempio, il software possa sia mostrare un messaggio di errore all'utente sia scrivere il messaggio su un file di log. Se

#figure[
  #image("uml/error_handler_cd.png", width: 210pt)
]

=== Esecuzione asincrona di `BookService`

Poiché le operazioni eseguite da `BookService` potrebbero essere _lente_ a seconda delle dimensioni della libreria virtuale, la loro esecuzione viene delegata a un `Thread` a parte. A tale scopo, viene utilizzato un `ExecutorService`, configurato attraverso `Executors.newCachedThreadPool`. In attesa del completamento del `Future` all'utente viene mostrato un elemento grafico che indica un "caricamento".   

=== #smallcaps[Composite] per la validazione a cascata dei form

Per inserire o modificare i libri è stato creato un form per permettere all'utente di inserire tutti i dati necessari. Poiché l'utente potrebbe commettere errori durante la digitazione oppure non riempire tutti i campi obbligatori, è stato necessario implementare meccanismi di validazione direttamente nell'interfaccia utente.

Si può affermare che: un form è valido se e solo se tutti i suoi "sottoform" sono validi. Data la natura intrinsecamente ricorsiva del problema, anche qui è stato opportuna impiegare il pattern #smallcaps[Composite], per mezzo dell'interfaccia `Validable` e della classe `CompositeValidable`. Nel contesto di JavaFX, gli oggetti che estendono o implementano le classi sono i _controller_. 

#figure[
  #image("uml/validable_cd.png", width: 230pt)
]

Nel caso in cui un _controller_ estenda `CompositeValidable` è esso stesso responsabile di mantenere aggiornata la propria lista di validatori figli. Solitamente i sottocomponenti fissi sono aggiunti durante l'esecuzione del metodo `initialize`, i sottocomponenti dinamici quando l'utente compie l'azione per rimuoverli o aggiungerli (come avviene nel caso del sottocomponente `EditionComponentController`). In un form quello che si crea in memoria sarà la seguente gerarchia in figura.

#figure[
  #image("uml/validable_controllers_od.png")
] 

Di conseguenza eseguire i metodo `validate` o `isValid` su `AddBookController` risulterà nella visita di tutti i `Validable` figli appartenenti a questa gerarchia. 

Poiché un _controller_ conosce già i propri figli l'utilizzo del #smallcaps[composite] potrebbe sembrare eccessivo. È bene però stressare il fatto che la scrittura manuale del codice necessario per combinare il risultato della validazione dei sottocomponenti potrebbe fare incorrere in errori. Inoltre, nella maggior parte di casi tale codice è triviale, poiché si tratta di calcolare la catena di $and$ tra tutti i risultati dei sottocomponenti. In casi in cui si necessità più personalizzazione si può in ogni caso eseguire un override dell'interfaccia o implementare manualmente `Validable`. Di conseguenza, l'utilizzo del #smallcaps[composite] è più che giustificato per evitare migliorare la struttura del software e per evitare errori.

=== Composizione ricorsiva dei filtri nell'interfaccia utente

Essendo i filtri una struttura ricorsiva all'interno del dominio, è conveniente che tale rappresentazione sia assunta anche a livello di DTO. Tuttavia a questo livello conviene utilizzare classi concrete ben definite al posto delle interfacce, in particolar modo se i DTO potrebbe essere essere serializzati in futuro in un determinato formato. 

#figure[
  #image("uml/bookdto_cd.png", width: 300pt)
]

Grazie a questa gerarchia di classi è possibile assemblare ricorsivamente la struttura attraverso una serie la nidificazione dei dialoghi `SearchCreateComposite` e `SearchCreateLeaf`. 

=== Notifica evento a livello applicazione

Per ricevere notifiche asincrone sul cambiamento di stato dell'applicazione, è stato impiegato il pattern #smallcaps[Observer]. Infatti, i _controller_ possono sottoscriversi ai servizi esposti a livello applicazione implementando l'interfaccia `Observer<ApplicationEvent>`. Questo tipo di #smallcaps[Observer] è di tipo _push_.

=== #smallcaps[Strategy] per stabilire se ricercare attraverso la barra di ricerca oppure i filtri.

Il pattern #smallcaps[Strategy] è utilizzato per semplificare il cambio di caso d'uso nell'utilizzo del componente di ricerca. Infatti, se l'utente preme il pulsante "Cerca", bisogna invocare un caso d'uso differente rispetto a quando egli preme il pulsante "Filtro".

#figure[
  #image("uml/search_strategy_cd.png")
]

Bisogna considerare che è possibile implementare lo stesso caso d'uso riutilizzando i filtri a livello di presentazione, ma ciò violerebbe l'architettura descritta nella sezione C: in tal caso, infatti, anche il livello di presentazione realizza i casi d'uso, non solo quello applicativo. Violare questo vincolo significa permettere che i casi d'uso siano distribuiti all'interno dell'interfaccia. Ciò rende difficile sia la comprensione del codice (non è più centralizzato) sia la portabilità: bisogna implementare nuovamente i casi d'uso in caso l'interfaccia venga cambiata.

= Come il progetto soddisfa i requisiti funzionali (FRs) e quelli non funzionali (NFRs) 

== Requisiti funzionali

- *S01, S02, S03*: i servizi mettono a disposizione un form che consente all'utente di aggiungere un libro alla propria libreria e, una volta aggiunto, modificarlo o eliminarlo (facendo doppio clicco su di esso all'interno della tabella). Per favorire l'inserimento corretto dei dati, il form include meccanismi di suggerimento automatico per nomi di autori e generi già presenti nel sistema, contattando in modo asincrono il livello di applicazione. Inoltre, vengono eseguite verifiche sulla presenza e la corretta formattazione dei dati inseriti, grazie alla flessibilità fornita dal pattern #smallcaps[Composite]. Al momento del salvataggio, il livello di applicazione si occupa di garantire il rispetto di tutti i vincoli previsti attraverso gli oggetti e i servizi nel livello di dominio. Dopo l'aggiunta, lo stesso form consente all'utente di modificare o rimuovere il libro. In questo caso oltre al pulsante "Salva", è presente anche il pulsante "Rimuovi". Questa flessibilità è resa possibile dall'indipendenza del controller e del file FXML del form rispetto al contesto in cui vengono utilizzati.

- *S04*: i libri sono visualizzati all'interno di una tabella, la quale è popolata accedendo al `BookService` (servizio di livello applicativo). Esso espone un'interfaccia per specificare la pagina corrente, la lunghezza della pagina e l'ordinamento degli elementi.  A tale scopo, l'interfaccia fornisce due pulsanti per gestire la paginazione e una _combobox_ attraverso cui è possibile selezionare l'ordinamento desiderato. In caso di aggiornamento dello stato della libreria virtuale, la visualizzazione è automaticamente aggiornata grazie al pattern #smallcaps[Observer].

- *S05*: il pattern #smallcaps[Composite] permette la composizione ricorsiva di filtri secondo quanto già discusso in F.1. All'interno dell'interfaccia grafica questa funzionalità è fornita per mezzo di un pulsante che mostra il dialogo di inserimento di un gruppo di filtri. Da qui l'utente può ricorsivamente arricchire il filtro aprendo sottodialoghi con gli appositi tasti.

- *S06*: La libreria viene salvata in permanente in memoria secondaria all'interno di un file JSON grazie alla famiglia di classi fornita da `JsonDataAccessProvider`.

- *S07*: grazie ai componenti forniti da JavaFX, la tabella fornisce un menù a tendina cliccando sul campo d'interesse. Il menù permette did scegliere un nuovo valore tra quelli suggeriti per la valutazione o lo stato di lettura. In questo modo è possibile evitare di accedere alla schermata di modifica del libro. Una volta effettuata la modifica, in maniera asincrona, la richiesta viene inviata al servizio di livello applicativo.

- *S08*: questa funzionalità è fornita tramite una barra di testo con accanto un pulsante "Cerca". Il caso d'uso viene differenziato da S05 grazie all'utilizzo del pattern #smallcaps[Strategy] come descritto in F.3.9.

== Requisiti non funzionali

- *NFR01*: l'applicazione non fa uso di servizi esterni e remoti, poiché si affida unicamente ai dati forniti da `JsonBookRepository`. Inoltre, fornisce unicamente un'interfaccia pensata esclusivamente per ambienti desktop.

- *NFR02*: l'applicazione fornisce un'interfaccia utente grafica per mezzo della piattaforma software JavaFX.

- *NFR03*: l'applicazione fornisce un'interfaccia semplice e messaggi di errore dalla facile comprensione. All'interno delle schermate sono solo presenti elementi di controllo _standard_, senza particolare modifiche allo stile originario. La coerenza dell'interfaccia è realizzata grazie alla cura nei confronti della riutilizzabilità dei componenti. La responsività dell'interfaccia viene mantenuta grazie all'esecuzioni delle operazioni su _thread_ separati grazie all'utilizzo dell'_Executor Framework_.

- *NFR04*: il sistema è sviluppato in Java. Tutte le dipendenze esterne incluso JavaFX sono portabili facilmente da un ambiente operativo ad un altro.

- *NFR05*: il sistema è scalabile in lettura limitatamente alla memoria primaria disponibile. Per garantire la scalabilità in scrittura e la non dipendenza dalla dimensione dalla memoria primaria è necessario l'ausilio di un DBMS.

#show heading.where(level: 1): set heading(numbering: none)
#show heading.where(level: 2): set heading(numbering: none)

= Appendice. Prototipo

Tutti i requisiti fuzionali descritti sono stati implementati nel prototipo facendo uso di tutte le decisione progettuali descritte nelle sezioni precedenti. 

== Testing

Il soddisfacimento dei requisiti funzionali è stato in parte verificato attraverso l'ausilio di test di unità, rieseguiti ad ogni aggiunta di nuove funzionalità o modifiche per verificare la presenza di regressioni. In particolare, i test (prettamente di tipo _black box_) hanno verificato la correttezza del funzionamento dei servizi di livello applicazione. Nel prototipo l'unico servizio implementato è `BookService`, che include tutti i casi d'uso documentati.

Il testing di unità sul `BookService` è stato reso possibile grazie alla famiglia di classi che implementano la persistenza _in-memory_, la cui correttezza è stata verificata in parte grazie ad una serie di test dedicati. 

Ulteriori test sono stati scritti per verificare la correttezza della gerarchia di classi `Filter<T>` e della validazione dell'ISBN, poiché sono componenti particolarmente complessi. Tuttavia, potrebbe tornare utile scrivere test d'unità direttamente sugli oggetti di dominio, in modo tale da isolare meglio gli errori che potrebbe incorrere nella verifica di regole o degli invarianti di classe che non sono rintracciabili per mezzo dei test precedentemente citati.

Data la relativa semplicità delle interazioni all'interno dell'interfaccia utente, si è preferito eseguire test manuali per verificare la correttezza, le prestazioni e la robustezza di quest'ultima. 

== Interfaccia grafica 

Di seguito sono mostrati alcuni _screenshot_ a dimostrazione dell'interfaccia grafica realizzata.

#figure(caption: "Finestra iniziale.")[
  #image("screenshots/dashboard.png", width: 320pt)
]

#figure(caption: "Dialogo di modifica di un libro preesistente (il dialogo di aggiunta di un libro è pressoché uguale).")[
  #image("screenshots/edit_book.png", width: 320pt)
]

#figure(caption: "Modifica rapida dello stato di lettura di un libro.")[
  #image("screenshots/quick_edit.png", width: 320pt)
]

#figure(caption: "Esempio di messaggio di validazione all'interno del form.")[
  #image("screenshots/validation.png", width: 320pt)
]

#figure(caption: "Esempio di creazione di un filtro composito")[
  #image("screenshots/filter_group_creation.png", width: 320pt)
]

#figure(caption: "Esempio di creazione di una regola di filtro primitiva")[
  #image("screenshots/filter_rule_creation.png", width: 320pt)
]

#bibliography("refs.bib", title: "Riferimenti",)
