# HomeServiceProviderSystemFourthPhase

Program contains three roles including Admin, Expert and Client. Each expert has some skills in a Service and few Sub Services. System allows client to submit orders based on a sub service so that in the other side of the system experts can see related orders and they can set offers and wait for the client response. It also has a payment panel which has a form with captcha. The whole program is based on Spring boot, MVC and security.
As forth phase I added Spring Secutiry to the project so that since now signup and registration operation gets completed with a verification email (using JavaMailSender) which the user must click on the sent link in order to complete the signup process.
All passwords are hashed by BCrypt and users must login before doing any operation. The authentication of each user also will be checked and the accesses will be given to them based on their role (Admin, Expert and Client).
![Screenshot (2367) (2)](https://github.com/AliBondar/HomeServiceProviderSystemThirdPhase/assets/127098646/0b3a9235-d57d-4695-a2c2-14b0b5535aeb)
![Screenshot (2389)](https://github.com/AliBondar/HomeServiceProviderSystemFourthPhase/assets/127098646/60ebc014-7060-462b-911d-7d6aec32c9a7)
