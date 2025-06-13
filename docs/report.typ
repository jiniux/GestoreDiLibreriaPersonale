#import "template.typ": *

#show: template.with(
  full_name: "Dolorem Sit Amet",
  professor: "Lorem Ipsum", 
  student_number: "123456",
  email: "info@example.com"
)

#show heading.where(level: 1): set heading(numbering: none)

= List of Challenging/Risky Requirements or Tasks

#show heading.where(level: 1): set heading(numbering: "A.")

#table(
  columns: (1fr, 1fr, 1fr, 1fr),
  [*Challenging Task*], [*Date task is identified*], [*Date challenge is resolved*], [*Explanation on how the challenge has been managed*],
)


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

Un libro deve avere almeno una  *_edizione_* associata: l'utente deve inserire almeno un edizione durante la fase di aggiunta di un libro. Ogni  *_edizione_* è descritta dai seguenti attributi:

- *ISBN*: codice univoco associato ad ogni _*edizione*_. L'utente deve poter inserire sia L'ISBN-10 sia L'ISBN-13. L'utente deve essere in grado di inserire l'ISBN specificando le cifre da cui è composto (i caratteri che non sono cifre possono essere ignorate). Nel sistema non possono esistere due edizioni con lo stesso ISBN, anche se di libri diversi. In aggiunta, è necessario che il formato dell'ISBN sia valido (i.e. la sua lunghezza in cifre) e che la sua integrità sia verificata per mezzo delle _check digits_.

- *Autori aggiuntivi* dell'_*edizione*_ (_facoltativo_).

- *Numero edizione* (_facoltativo_).

- *Titolo edizione* (_facoltativo_).

- *Editore*. L'utente deve specificare uno e un solo *_editore_* per _*edizione*_.

- *Formato* (_facoltativo_).

- *Lingua* (_facoltativa_).

- *Data di pubblicazione* (_facoltativa_). 
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
  - rispetto alla data di pubblicazione.
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


#nfr("NFR04", "Limite delle dimensioni dei dati inseriti dall'utente")[
  Il sistema deve implementare meccanismi di controllo sulla dimensione dei dati inseriti dall'utente per prevenire o mitigare impatti negativi sulle prestazioni del sistema stesso.
]

#nfr("NFR05", "Portabilità")[
  Il sistema deve essere facilmente portabile tra diversi sistemi operativi senza particolare sforzo da parte dell'utente.
]

#nfr("NFR06", "Scalabilità rispetto alla dimensione della libreria virtuale")[
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

È importante precisare che gli attributi di tipo `string` identificano stringhe non vuote, ossia stringhe che non sono costituite da soli spazi o caratteri simili. Benché nel codice molti dei campi siano descritti da appositi _oggetti valore_ al fine di garantire la loro integrità, si è deciso di inserire nel _class diagram_ solo il loro tipo primitivo per evidenziare meglio il tipo di dato. 

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



#show heading.where(level: 1): set heading(numbering: none)

#bibliography("refs.bib", title: "Riferimenti",)
