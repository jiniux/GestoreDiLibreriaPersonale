#import "template.typ": *

#show: template.with(
  full_name: "Dolorem Sit Amet",
  professor: "Lorem Ipsum", 
  student_number: "123456",
  email: "info@example.com"
)

= List of Challenging/Risky Requirements or Tasks

#table(
  columns: (1fr, 1fr, 1fr, 1fr),
  [*Challenging Task*], [*Date task is identified*], [*Date challenge is resolved*], [*Explanation on how the challenge has been managed*],
)


= Stato dell'arte

Di seguito sono riportati i principali risultati della ricerca condotta sullo stato dell'arte dei software dedicati alle gestione di librerie personali. 

== Calibre 

Nel panorama _software_ attuale, il principale punto di riferimento per la gestione di una libreria personale, sia in formato digitale che cartaceo, è rappresentato da *Calibre* @calibre. Rilasciato sotto licenza GPLv3, Calibre è un _software_ libero sviluppato principalmente per la gestione di librerie di _e-book_ e per facilitare la loro conversione nei diversi formati supportati dai principali _e-reader_. Tra le sue funzionalità più rilevanti si segnalano:

- *Inserimento e gestione dei libri*: è possibile aggiungere libri alla libreria a partire da un file presente sul proprio computer, oppure inserirli manualmente nel caso in cui il file non sia disponibile (fornendo ISBN oppure i metadati originali). L'utente può modificare i metadati associati a ciascun libro, come titolo, autore, descrizione, parole chiave (tag), ecc. e assegnare una valutazione personale ad ogni libro. È possibile visualizzare tutti i libri attraverso un'interfaccia tabellare e se necessario rimuoverli con un apposito pulsante.

- *Visualizzazione del contenuto*: se il libro è associato a un file, è possibile visualizzarne direttamente il contenuto tramite un'apposita GUI.

- *Funzioni di ricerca avanzata*: il sistema di ricerca consente di filtrare e individuare rapidamente i libri presenti nella libreria per mezzo di una sintassi semplice e ben documentata.

- *Conversione e modifica degli e-book*: Calibre permette di convertire i libri digitali in numerosi formati di _e-book_ e offre strumenti per modificare il loro contenuto.

- *Integrazione con dispositivi e-reader*: una volta collegato un _e-reader_ al computer, Calibre è in grado di riconoscerlo automaticamente e fornisce la possibilità all'utente di caricare un libro selezionato sul dispositivo.

A completamento delle funzionalità descritte, Calibre offre funzionalità aggiuntive come la possibilità di effettuare ricerche di libri su numerosi siti web direttamente dall'interfaccia del programma, nonché una funzione per scaricare e convertire in _e-book_ le notizie presenti sulle principali testate giornalistiche di tutto il mondo.

Attualmente, Calibre risulta essere la soluzione migliore per chi necessita di catalogare i propri libri digitali e non: è un prodotto _software_ conveniente (è gratuito senza compromettere la privacy dell'utente finale) funzionale, _cross-platform_ e maturo (è in continuo sviluppo da circa 20 anni).

#bibliography("refs.bib", title: "Riferimenti",)