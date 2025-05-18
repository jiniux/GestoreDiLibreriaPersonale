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
