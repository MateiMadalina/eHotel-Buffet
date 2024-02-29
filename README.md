# eHotel Buffet

## About Project

In the hospitality industry, hotels face challenges in balancing customer satisfaction and operational efficiency, especially in managing their breakfast buffets. Staff shortages can lead to dissatisfaction among guests due to empty plates or excessive food waste. To address these challenges, we introduce the eHotel Buffet Simulator. This Java application aims to automate buffet management and optimize operations to minimize dissatisfaction among guests and reduce food waste.

## How it works
When running the program in the console, the user will be prompted to enter the start and end dates of the stay, as well as a day within that range for which the breakfast simulation will be conducted. Depending on the estimated number of people expected to attend, the eHotel Breakfast Buffet Simulator calculates the quantity of each food item for each serving cycle and manages replenishment accordingly to minimize waste and ensure guest satisfaction. Prior to starting the simulation, several rules are established within the breakfast algorithm: A season spans between a minimum of 3 days and a maximum of 1 month. Each guest can stay for a duration ranging from a minimum of 3 days to a maximum of 7 days. Following 3 cycles, any meal labeled as SHORT is considered expired and discarded. Upon breakfast completion, all meals labeled as SHORT and MEDIUM are disposed of.

## Demo
The application is run from the "EHotelBuffetApplication" file which contains the main class within it. After running the application, we will receive a welcome message followed by a prompt requesting input for a start date, end date and to choose a date from that interval.



After choosing the date, the breakfast simulation will begin.







