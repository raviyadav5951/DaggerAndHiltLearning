# DaggerAndHiltLearning

## Moving common code to BaseViewMvc(Section 5)

1.  First moved the listeners into BaseViewMvc ( Chapter - 16)
2.  Now moving the findViewByID and also we need rootview along with that so we are moving the dependencies which are required to make UI methods available ( Chapter - 17)
3.  Extracting the logic code,(api use cases) from QuestionListActivity into separate class FetchQuestionUseCase.kt ( Chapter - 18)
4.  Extracted the logic code from QuestionDetail to FetchQuestionUseCase.kt
5.  Separating error dialog fragment code from UI.( Chapter - 19)
6.  Separating the navigation logic ( Chapter -20)

## Extracting the common code from usecases class i.e networking code dependency

7. Passing dependency of StackoverflowApi to usecases classes
8. Our Activity classes only require stackoverflow api reference but we are initializing retrofit and 
sending all over the classes which was adding duplicate code.
   So we initialised the retrofit and stackoverflow api in myapplication and passed as the constructor in use cases classes.
   (Video-24)
9.    