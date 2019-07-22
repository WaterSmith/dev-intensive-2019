package ru.skillbranch.devintensive.models

import androidx.core.text.isDigitsOnly

class Bender(var status:Status = Status.NORMAL, var question:Question = Question.NAME) {
    private var phrase:String = ""

    fun askQuestion():String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer:String): Pair<String,Triple<Int,Int,Int>>{
        phrase = "${question.validationError}\n"
        if (question.validateAnswer(answer)) {
            if (question.checkAnswer(answer)) {
                phrase =  if (question.ordinal==Question.values().lastIndex) "" else "Отлично - ты справился\n"
                question = question.nextQuestion()
            } else {
                phrase = "Это неправильный ответ\n"
                status = status.nextStatus()
                if (status.ordinal==0) {
                    phrase = "Это неправильный ответ. Давай все по новой\n"
                    question = Question.NAME
                }
            }
        }
        return "$phrase${question.question}" to status.color
    }

    enum class Status(val color:Triple<Int,Int,Int>){
        NORMAL(Triple(255,255,255)),
        WARNING(Triple(255,120,0)),
        DANGER(Triple(255,60,60)),
        CRITICAL(Triple(255,0,0));

        fun nextStatus():Status  =
            if (this.ordinal< values().lastIndex)
                values()[this.ordinal+1]
            else values()[0]

        fun previusStatus():Status  =
            if (this.ordinal > 0)
                values()[this.ordinal-1]
            else values()[0]
    }

    enum class Question(val question:String, val answers:List<String>, val validationError:String = ""){
        NAME("Как меня зовут?",
            listOf("бендер", "bender"),
            "Имя должно начинаться с заглавной буквы"){
            override fun nextQuestion():Question = PROFESSION
            override fun validateAnswer(answer:String):Boolean = (answer.length > 0 && answer[0].isUpperCase())
        },
        PROFESSION("Назови мою профессию?",
            listOf("сгибальщик", "bender"),
            "Профессия должна начинаться со строчной буквы"){
            override fun nextQuestion():Question = MATERIAL
            override fun validateAnswer(answer:String):Boolean = (answer.length>0 && answer[0].isLowerCase())
        },
        MATERIAL("Из чего я сделан?",
            listOf("металл", "железо", "дерево", "iron", "metal", "wood"),
            "Материал не должен содержать цифр"){
            override fun nextQuestion():Question = BDAY
            override fun validateAnswer(answer:String):Boolean = (!answer.contains("[0-9]".toRegex()))
        },
        BDAY("Когда меня создали?",
            listOf("2993"),
            "Год моего рождения должен содержать только цифры"){
            override fun nextQuestion():Question = SERIAL
            override fun validateAnswer(answer:String):Boolean = (answer.isDigitsOnly())
        },
        SERIAL("Мой серийный номер?",
            listOf("2716057"),
            "Серийный номер содержит только цифры, и их 7"){
            override fun nextQuestion():Question = IDLE
            override fun validateAnswer(answer:String):Boolean = (answer.isDigitsOnly() && answer.length==7)
        },
        IDLE("На этом все, вопросов больше нет",
            listOf()){
            override fun nextQuestion():Question = IDLE
            override fun validateAnswer(answer:String):Boolean = true
            override fun checkAnswer(answer:String):Boolean = true
        };

        abstract fun nextQuestion():Question
        abstract fun validateAnswer(answer:String):Boolean
        open fun checkAnswer(answer:String):Boolean = this.answers.contains(answer.toLowerCase())
    }
}