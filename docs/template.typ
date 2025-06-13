
#let template(
  professor: "",
  full_name: "",
  student_number: "",
  email: "",
  body
) = [
  #set page("a4")

  #set text(font: ("XCharter", "Bitstream Charter"))

  #page([
  #align(center)[
    #grid(
      columns: (1fr,),
      rows: (auto, auto),
      row-gutter: 10pt,
      text(size: 24pt)[Ingegneria del Software], 
      text(size: 16pt)[2024 – 2025],
    )
  ]

  #v(0.25cm)

  #align(center)[
    #text(size: 12pt)[Docente: Prof. #professor]
  ]

  #v(1cm)

  #align(center)[
    #text(size: 20pt, weight: "bold")[Gestore di Libreria Personale] 
  ]

  #v(1cm)

  #table(
    columns: (auto, 1fr),
    stroke: 0.5pt,
    inset: 5pt,
    align: center,
    [*Data*], [#datetime.today().display("[day]/[month]/[year]")],
    [*Documento*], [Documento Finale – D3],
  )

  #v(1cm)

  #align(center)[
    #text(size: 14pt, weight: "bold")[Team Members] 
  ]

  #table(
    columns: (1fr, auto, 1fr),
    stroke: 0.5pt,
    inset: 5pt,
    align: center,
    [*Nome e Cognome*], [*Matricola*], [*E-mail address*],
    [#full_name], [#student_number], [#email],
  )

  ])
  
  #set par(leading: 1em, justify: true)

#set list(indent: 1em, body-indent: 0.5em)
#set enum(indent: 1em, body-indent: 0.5em)

  #show heading.where(level: 1): set heading(numbering: "A.")
  #show heading.where(level: 1): set block(spacing: 1em)
    
  #show heading.where(level: 2): set heading(numbering: "A.1.")
  #show heading.where(level: 2): set block(above: 1.5em, below: 1em)
  #show heading.where(level: 2): set text(weight: "bold")

  #show heading.where(level: 1): it => {
    pagebreak(weak: true)
    it
  }

  #let figure_spacing = 0.5em
#show figure: it => {
  if it.placement == none {
    block(it, inset: (y: figure_spacing))
  } else if it.placement == top {
    place(
      it.placement,
      float: true,
      block(width: 100%, inset: (bottom: figure_spacing), align(center, it))
    )
  } else if it.placement == bottom {
    place(
      it.placement,
      float: true,
      block(width: 100%, inset: (top: figure_spacing), align(center, it))
    )
  }
}

#show figure.caption: it => {
  align(box(align(it, left)), center)
}



  #counter(page).update(1)

  #set page(
    paper: "a4",  
    numbering: "1",
    header: align(bottom)[
      #table(
      columns: (1fr, auto),
      stroke: 0pt,
      row-gutter: 0pt,
      inset: 0pt,
      align: center + horizon,

      [*Ingegneria del software* #h(5pt) 2024 – 2025 #h(1fr) ],
      [_Deriverable_ di progetto] 
    )
  ]
  )

  #set table(stroke: 0.5pt)

  #body
]
