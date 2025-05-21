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
    [*Complessità*], [#complexity],
    table.cell(inset: (y: 15pt, x: 15pt), colspan: 4, description)
  )
}


#service("S01", "Aggiunta di un libro alla libreria", "Alta", "Media", [
  
L'utente deve essere in grado aggiungere facilmente e rapidamente un _*libro*_ alla propria libreria virtuale a partire dai seguenti dati (alcuni campi sono _facoltativi_, ossia l'utente può non inserirli):

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

Infine, un utente deve avere la possibilità di aggiungere un'immagina ad ogni edizione rappresentativa della sua copertina. 
])

#service("S02", "Rimozione di un libro dalla libreria virtuale", "Alta", "Bassa")[

L'utente deve essere in grado di eliminare un *_libro_* dalla libreria. La sua eliminazione deve comportare la rimozione di tutte le sue *_edizioni_* dal sistema. Inoltre, l'utente deve avere la possibilità di rimuovere una *_edizione_* da un libro. Se un *_libro_* ha una *_edizione_*, la rimozione di quest'ultima deve comportare la rimozione del _*libro*_ dalla libreria. 
]

#service("S03", "Modifica informazioni di un libro dalla libreria virtuale", "Alta", "Media")[
L'utente deve essere in grado di modificare le informazioni relative al *_libro_* e/o alle sue edizioni già espresse in S01. Ogni modifica deve soddisfare i vincoli specificati in S01.
]


#service("S04", "Visualizzazione dei libri presenti nella libreria virtuale", "Alta", "Media")[
  I *_libri_* devono essere presentati all'utente in una vista d'insieme, che può essere visualizzata in formato *tabella* oppure *lista*. L'utente deve poter scegliere liberamente tra le due modalità di visualizzazione. Se non è possibile visualizzare in un'unica vista tutti libri presenti nella libreria virtuale, è necessario ricorrere alla paginazione. 

  In entrambe le viste, per ciascun *_libro_* devono essere mostrati almeno i seguenti elementi: *titolo*, *autori* e *copertina*. La copertina visualizzata deve corrispondere a quella eventualmente indicata dall'utente. Se l'utente non ha selezionato una copertina specifica, deve essere mostrata quella relativa all'*_edizione_* più recente del *_libro_*.
]


#service("S05", "Ricerca di un libro nella libreria virtuale", "Media", "Alta")[
  Il sistema deve fornire all'utente una funzione di ricerca per trovare più rapidamente i *_libri_* a partire dai loro attributi (*_edizioni_* incluse).

La ricerca deve essere basata sulla valutazione di una funzione booleana $F(x)$ applicata a ciascun *_libro_*. Un libro viene selezionato se $F(x)$ restituisce "vero".

La funzione $F(x)$ deve poter essere costruita combinando $n$ funzioni più semplici $G_i (x)$. Ogni $G_i (x)$ deve essere formulata mediante un confronto tra il valore di un attributo del *_libro_* $x$ e un valore di riferimento, utilizzando operatori di confronto come $=$, $>$, $<$, $>=$, $<=$, laddove applicabili. Queste funzioni devono poter essere combinate usando operatori logici binari ("e", "o") per esprimere condizioni complesse.

I risultati della ricerca devono essere presentati nella medesima di vista descritta in S04.
]

#service("S06", "Salvataggio della libreria in modo persistente su memoria secondaria", "Alta", "Alta")[
  I dati relativi alla libreria virtuale devono essere salvati in modo persistente su memoria secondaria, utilizzando un'apposita base di dati. Il salvataggio deve avvenire automaticamente a ogni modifica dello stato della libreria.
]

#service("S07", "Esportazione/importazione della libreria virtuale", "Bassa", "Media")[
L'utente deve poter esportare la propria libreria virtuale su file, in modo da poterla importare successivamente.

Prima di avviare l'esportazione o l'importazione, il sistema deve consentire all'utente di selezionare i libri da includere nell'operazione. Durante l'importazione, il sistema deve verificare che i libri importati rispettino i vincoli definiti nel requisito S01. L'importazione di libri non conformi deve essere impedita.
]

== Requisiti non funzionali 

#let nfr(id, name, description) = {
  table(
    columns: (auto, 1fr, auto, 1fr),
    [#id ], table.cell(colspan: 3)[#name],
    table.cell(inset: (y: 15pt, x: 15pt), colspan: 4, description)
  )
}

#show heading.where(level: 1): set heading(numbering: none)

#bibliography("refs.bib", title: "Riferimenti",)