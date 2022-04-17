# DaggerAndHiltLearning

## Moving common code to BaseViewMvc(Section 5)

1.  First moved the listeners into BaseViewMvc ( 16)
2.  Now moving the findViewByID and also we need rootview along with that so we are moving the dependencies which are required to make UI methods available(17)
3.  Extracting the logic code,(api use cases) from QuestionListActivity into separate class FetchQuestionUseCase.kt
4.  Extracted the logic code from QuestionDetail to FetchQuestionUseCase.kt
