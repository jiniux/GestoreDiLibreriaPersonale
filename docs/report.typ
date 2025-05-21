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

#show heading.where(level: 1): set heading(numbering: none)

#bibliography("refs.bib", title: "Riferimenti",)