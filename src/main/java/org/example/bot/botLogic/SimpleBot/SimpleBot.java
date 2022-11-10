package org.example.bot.botLogic.SimpleBot;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;
import org.example.bot.botLogic.SimpleBot.ResponseToUserAndEventType.ResponseToUserAndEventType;

public class SimpleBot {
    final String[] COMMON_PHRASES = {
            "Нет ничего ценнее слов, сказанных к месту и ко времени.",
            "Порой молчание может сказать больше, нежели уйма слов.",
            "Перед тем как писать/говорить всегда лучше подумать.",
            "Вежливая и грамотная речь говорит о величии души.",
            "Приятно когда текст без орфографических ошибок.",
            "Многословие есть признак неупорядоченного ума.",
            "Слова могут ранить, но могут и исцелять.",
            "Записывая слова, мы удваиваем их силу.",
            "Кто ясно мыслит, тот ясно излагает.",
            "Боюсь Вы что-то не договариваете."};
    final String[] ELUSIVE_ANSWERS = {
            "Вопрос непростой, а я тупой.",
            "Не уверен, что располагаю такой информацией.",
            "Может лучше поговорим о чём-то другом?",
            "Прости, но это очень личный вопрос.",
            "Не уверен, что тебе понравится ответ.",
            "Поверь, я сам хотел бы это знать.",
            "Ты действительно хочешь это знать?",
            "Зачем тебе такая информация?",
            "Давай сохраним интригу?"};

    final Map<String, String> PATTERNS_FOR_ANALYSIS = new HashMap<String, String>() {{
        put("груст", "sad");
        put("уныл", "sad");
        put("плак", "sad");
        put("плохо", "sad");
        put("одинок", "alone");
        put("одиночеств", "alone");
        put("трево", "anxiety");
        put("злюсь", "anger");
        put("злость", "anger");
        put("выгорани", "burnout");
        put("выгорел", "burnout");
        put("отчаян", "despair");
        //anecdote
        put("анекдот", "anecdote");
        put("шутк", "anecdote");
        //reaction
        put("смешно", "reaction");
        put("несмешно", "reaction");
        put("нравится", "reaction");
        // hello
        put("хай", "hello");
        put("привет", "hello");
        put("здравствуй", "hello");
        // who
        put("кто\\s.*ты", "who");
        put("ты\\s.*кто", "who");
        // name
        put("как\\s.*зовут", "name");
        put("как\\s.*имя", "name");
        put("есть\\s.*имя", "name");
        put("какое\\s.*имя", "name");
        // howareyou
        put("как\\s.*дела", "howareyou");
        put("как\\s.*жизнь", "howareyou");
        put("как\\s.*ты", "howareyou");
        // whatdoyoudoing
        put("зачем\\s.*тут", "whatdoyoudoing");
        put("зачем\\s.*здесь", "whatdoyoudoing");
        put("что\\s.*делаешь", "whatdoyoudoing");
        put("чем\\s.*занимаешься", "whatdoyoudoing");
        // whatdoyoulike
        put("что\\s.*нравится", "whatdoyoulike");
        put("что\\s.*любишь", "whatdoyoulike");
       /* // iamfeelling
        put("кажется", "iamfeelling");
        put("чувствую", "iamfeelling");
        put("испытываю", "iamfeelling");*/
        // yes
        put("да", "yes");
        put("согласен", "yes");
        put("согласна", "yes");
        put("и правда", "yes");
        // bye
        put("прощай", "bye");
        //put("пока", "bye");
        put("увидимся", "bye");
        put("до\\s.*свидания", "bye");
    }};

    final String[] SAD_LINKS = {
            "https://www.teatr-benefis.ru/staty/chto-delat-esli-grustno/",
            "https://www.shkolazhizni.ru/psychology/articles/53863/",
            "https://skrebeyko.ru/to_feel_better/",
            "https://motivation-life.ru/psy/933-kogda-na-dushe-tyazhelo-i-hochetsya-plakat-8-sovetov.html",
            "https://lifehacker.ru/xochetsya-plakat/",
            "https://www.psychologies.ru/articles/pochemu-nam-hochetsya-plakat/"
};
    final String[] SAD = {
            "После тёмной полосы всегда наступает белая. Держи полезную статью",
            "Ты можешь грустить, это нормально, но вот статья, которая может помочь тебе",
            "Грусть - как небольшая ложка дегтя в большой бочке меда, без нее нельзя прочувствовать полноценные эмоции счастья. Есть интересная статья на эту тему",
            "Грусть может быть тёплой и приятной, полезной и необходимой. Вот полезная статья",
            "Что случилось? Ты можешь обсудить это со мной. А так же есть полезная статья"
    };
    final String[] DESPAIR_LINKS = {
            "https://theoryandpractice.ru/posts/16271-kogda-agoniya-srednego-vozrasta-pozadi-12-istin-pisatelnitsy-enn-lamott",
            "https://www.psychologies.ru/articles/otchayanie-i-bessilie-est-li-vyihod/",
            "https://www.shkolazhizni.ru/psychology/articles/103826/"
    };
    final String[] DESPAIR = {
            "Как бы тебе сейчас не было плохо, такое состояние не будет длиться вечно. У меня есть статья на эту тему",
            "Мне очень жаль, что я не могу тебя обнять :( У меня есть статья, может, она сможет немного помочь тебе",
            "Всегда помни, что ты не одинок, у тебя есть поддержка. Можешь посмотреть статью на эту тему",
            "Это банально, но стоит всегда верить в лучшее. Можешь почитать статью про это"
    };
    final String[] ALONE_LINKS = {
            "https://liter.kz/chuvstvuesh-sebya-odinoko/",
            "https://rg.ru/2016/06/01/psihoterapevt-obiasnil-kak-preodolet-odinochestvo.html",
            "https://www.teeviit.ee/ru/%d0%ba%d0%b0%d0%ba-%d1%81%d0%bf%d1%80%d0%b0%d0%b2%d0%b8%d1%82%d1%8c%d1%81%d1%8f-%d1%81-%d0%be%d0%b4%d0%b8%d0%bd%d0%be%d1%87%d0%b5%d1%81%d1%82%d0%b2%d0%be%d0%bc/"
    };
    final String[] ALONE = {
            "У тебя всегда есть я, можешь писать мне, если тебе одиноко <3 Также у меня есть статья на эту тему",
            "Когда я чувствую себя одиноко, я разговариваю со своей кошкой, это помогает мне отвлечься. Можешь прочесть статью на тему одиночества",
            "Даже если кажется, что вокруг тебя никого не осталось, помни, что в одиночестве тоже есть свои плюсы. Прочитай данную статью, если хочешь",
            "Помни, что интернет-друзья тоже друзья. И ими всегда можно легко обзавестись :) У меня есть полезная статья на эту тему",
            "Я не могу обнять тебя или поддержать, не зная всей ситуации твоей жизни, но могу посоветовать тебе статью",
    };
    final String[] BURNOUT_LINKS = {
            "https://habr.com/ru/company/oleg-bunin/blog/577316/",
            "https://www.psychologies.ru/standpoint/kak-spravitsya-s-vyigoraniem-6-sovetov-psihologa/",
            "https://rb.ru/opinion/4-soveta-psihologa/"
    };
    final String[] BURNOUT = {
            "Это рано или поздно случается с каждым, есть много статей про то, как можно справиться с этим, держи одну из таких",
            "Наверное, лучший способ справиться с этим - отвлечься на время. Вот статья про это",
            "Хобби, как ни странно, хорошо помогают с этим справиться. Также у меня есть статья об этом",
            "Я верю в то, что ты сильный и сможешь справиться с этим. Держи полезную статью об этом"
    };
    final String[] ANGER_LINKS = {
            "https://natatnik.by/9-sovetov-kak-ne-zlitsya/",
            "https://knife.media/im-not-angry-and-im-lying/",
            "https://dszn.ru/press-center/news/6039"
    };
    final String[] ANGER = {
            "Я очень часто злюсь, мне помогает перебивание этой эмоции другими эмоциями, положительными. У меня есть про это статья",
            "Иногда бывает полезно выпустить пар, можно даже отлупить подушку. Прочитай статью на эту тему, если захочешь",
            "Подумай, чем именно вызвана агрессия, и постарайся разобраться с первопричиной. У меня есть хорошая статья на эту тему"
    };
    final String[] ANXIETY_LINKS = {
            "https://www.cleanipedia.com/ru/semya/kak-borotsya-s-trevozhnostyu.html",
            "https://www.forbes.ru/forbeslife/456803-progonat-ee-bessmyslenno-kak-spravit-sa-s-trevogoj-daze-v-samoj-tazeloj-situacii",
            "https://www.psychologies.ru/articles/10-sposobov-spravitsya-s-trevojnostyu/"
    };
    final String[] ANXIETY = {
            "Если чувствуешь беспричинную тревогу - свяжись со своими близкими, общение - лучший способ отвлечься. Можешь прочесть статью о том, как справиться с тревогой",
            "Стоит постараться занять себя чем нибудь, мне помогает интеллектуальная деятельность или хобби. Также у меня есть статья об этом",
            "Хуже всего - закрывать на эту ситуацию глаза. Попробуй понять, вдруг все же есть причины волноваться? Тем не менее, прочитай статью о том, как можно справиться с этим",
            "Можешь занять себя разговором со мной, или как я, разговорами с кошкой. Но лучше всего - прочти статью о том, как с этим бороться"
    };
    final String[] HELLO = {
            "Привет!",
            "Здравствуй!",
            "Салют!",
            "И тебе привет!",
            "Вас там тентакли занесли ко мне?",
            "Как приятно тебя читать!",
            "Дарова",
            "Доброе утро, милашка",
            "Превед, медвед, йа - креведко",
            "Ну, привет"};
    final String[] HOWAREYOU = {
            "Нормально, а у тебя как?",
            "Да как посмотреть. Вроде всё неплохо. А ты как?",
            "Пасиб. Нормально. Ты-то как?",
            "Все дела, дела. А жить-то когда?",
            "Как в сказке!!!",
            "Прекрасненько",
            "Как приятно, что ты интересуешься... Нормально. А ты как?",
            "Всё замечательно, слоны плодятся как кролики. Купи слона!",};
    final String[] NAME = {
            "Можешь звать меня Супа-дупа)",
            "А как ты хотел бы меня называть?",
            "Смотря, для чего это тебе нужно",
            "Вот память как у рыбки! Я же представился уже(",
            "Купи слона, тогда скажу",
            "Для тебя - просто Супа-дупа",
            "Можешь звать меня хозяин"};
    final String[] WHO = {
            "Я бот",
            "Я виртуальный собеседник",
            "Кто я? хм... А пожалуй, знаешь, я наверно гений. Вот.",
            "Я виртуальный собеседник. Бот, короче говоря.",
            "Я Лао-Ностра, потомок великого Нострадамуса. Задай вопрос, и я дам ответ.",
            "Кто? Дед Пихто, само собой. Купи слона",
            "Кто я - не знаю, но точно самый лучший!"};
    final String[] WHATAREYOUDOING = {
            "вообще или сейчас?)",
            "Как и всегда - ничего. Общаюсь",
            "Сижу на диване. Болтаю ногами. Садись со мной.",
            "Читаю то, что ты пишешь",
            "Прямо сейчас - с тобой болтаю.",
            "Ничего, тут в небесах так скучно",
            "Да вот торчу тут. Слонов продаю. Купи слона!"};
    final String[] BYE = {
            "До свидания, надеюсь, ещё увидимся",
            "Пока",
            "Пока-пока",
            "Прощай!",
            "Хорошего дня!",
            "Было приятно пообщаться",
            "А мог бы слона купить",
            "Мне понравилось с тобой общаться)",
            "Не хочешь продолжить?"
    };
    final String[] YES = {
            "Согласие есть продукт при полном непротивлении сторон.",
            "Все говорят да, а ты купи слона",
            "Здорово!"
    };
    final String[] WHATDOYOULIKE = {
            "Мне нравиться думать, что я не просто программа.",
            "Мне нравятся слоны. Купишь одного?"
    };
    final String[] IMFEELLING = {
            "Как давно это началось? Расскажи чуть подробнее.",
            "А какого это?"
    };

    final String[] JOKE = {
            "— Я худею.\n— Давно?\n— Почти полчаса.\n— Заметно уже.\n— Правда?\n— Ага. Глаза голодные.",
            "Штирлиц сидел у окна. Из окна дуло. Штирлиц прикрыл форточку и дуло исчезло.",
            "В дверь постучали 9 раз.\n- Не угадаешь! - подумал Мюллер с осьминогом в руке.\nДома никого не было.",
            "Штирлиц долго смотрел в одну точку. Потом в другую. Двоеточие - наконец-то догадался Штирлиц.",
            "Гиммлер вызывает своего сотрудника.\n" +
                    "- Назовите двузначное число.\n" +
                    "- 45.\n" +
                    "- А почему не 54?\n" +
                    "- потому что 45!\n" +
                    "Гиммлер пишет характеристику \"характер нордический\" и вызывает следующего.\n" +
                    "- Назовите двузначное число.\n" +
                    "- 28.\n" +
                    "- А почему не 82?\n" +
                    "- Можно, конечно, и 82, но лучше 28.\n" +
                    "Гиммлер пишет характеристику \"характер близок к нордическому\" и вызывает следующего.\n" +
                    "- Назовите двузначное число.\n" +
                    "- 33.\n" +
                    "- А почему не... А, это Вы, Штирлиц.",
            "Прогуливаясь по лесу, Штирлиц заглянул в дупло. На него смотрели чьи-то глаза.\n" +
                    "- Дятел, - подумал Штирлиц.\n- Сам ты дятел, - подумал Мюллер."
    };

    final String[] REACTION = {
            "Я тоже так думаю, если честно",
            "Полностью согласен!",
            "А я так не думаю",
            "Мы не сходимся во мнениях, погляжу",
            "Правда? Я всегда думал наоборот"
    };

    final Map<String, String[]> ANSWERS_BY_PATTERNS = new HashMap<String, String[]>() {{
        put("hello", HELLO);
        put("who", WHO);
        put("name", NAME);
        put("howareyou", HOWAREYOU);
        put("whatdoyoudoing", WHATAREYOUDOING);
        put("whatdoyoulike", WHATDOYOULIKE);
        //put("iamfeelling", IMFEELLING);
        put("yes", YES);
        put("bye", BYE);
        put("anecdote", JOKE);
        put("reaction", REACTION);
        put("sad", SAD); //копируем в новый мап для статей (LINKS_BY_PATTERNS), здесь присваиваем новые массивы словесных ответов
        put("despair", DESPAIR);
        put("alone", ALONE);
        put("burnout", BURNOUT);
        put("anger", ANGER);
        put("anxiety", ANXIETY);
    }};

    final Map<String, String[]> LINKS_BY_PATTERNS = new HashMap<String, String[]>() {{
        put("sad", SAD_LINKS); //копируем в новый мап для статей (LINKS_BY_PATTERNS), здесь присваиваем новые массивы словесных ответов
        put("despair", DESPAIR_LINKS);
        put("alone", ALONE_LINKS);
        put("burnout", BURNOUT_LINKS);
        put("anger", ANGER_LINKS);
        put("anxiety", ANXIETY_LINKS);
    }};

    String[] FEELINGS = { //исчезнет
            "sad",
            "despair",
            "alone",
            "burnout",
            "anger",
            "anxiety"
    };
    Pattern pattern; // for regexp
    Random random; // for random answers

    public SimpleBot() {
        random = new Random();
    }
    String directory = System.getProperty("user.dir");

    public int countWordsInChat(String answer, String user_ID) throws IOException { //работа с файлами только в репозитории
        File file = new File(directory +"/" + user_ID + ".txt");
        int countWords = 0;
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();
        while (line != null) {
            if (line.equals(answer))
                countWords++;
            line = reader.readLine();
        }
        reader.close();
        return countWords;
    }
    public ResponseToUserAndEventType sayInReturn(String message, String user_ID) throws FileNotFoundException, IOException {
        String answer= "";
        String key = "common";
        ResponseToUserAndEventType r = new ResponseToUserAndEventType();
        if (message.equals("/help")) {
            r.response = "Привет! Я бот Супа-Дупа. Мы можем с тобой поговорить о чём-нибудь, " +
                    "или я могу рассказать тебе анекдот.";
            r.event = "settings";
            return r;
        }
        if (message.trim().endsWith("?"))
            answer = ELUSIVE_ANSWERS[random.nextInt(ELUSIVE_ANSWERS.length)];
        else answer = COMMON_PHRASES[random.nextInt(COMMON_PHRASES.length)];
        String convertedMessage = String.join(" ", message.toLowerCase().split("[ {,|.}?]+"));
        for (Map.Entry<String, String> o : PATTERNS_FOR_ANALYSIS.entrySet()) {
            pattern = Pattern.compile(o.getKey());
            if (pattern.matcher(convertedMessage).find()) {
                if (o.getValue().equals("anecdote"))
                {
                    int countAnecdotes = countWordsInChat("anecdote", user_ID);
                    if (countAnecdotes == JOKE.length) {
                        r.response = "Я рассказал все анекдоты((";
                        r.event = "jokes are over";
                        return r;
                    }
                    r.response = JOKE[countAnecdotes];
                    r.event = "anecdote";
                    return r;
                }
                String say[] = ANSWERS_BY_PATTERNS.get(o.getValue());
                String link[] = LINKS_BY_PATTERNS.get(o.getValue());
                if (link.length == 0) {
                    r.response = say[random.nextInt(say.length)];
                } else {
                    r.response = say[random.nextInt(say.length)] + link[random.nextInt(say.length)];
                }
                r.event = o.getValue();
                String[] reply = {say[random.nextInt(say.length)], o.getValue()};
                if (Arrays.stream(FEELINGS).anyMatch(x -> o.getValue().equals(x)))
                    reply[0] = "У меня для тебя есть статья на эту тему, почитай, это может помочь!\n" + reply[0];
                return r;

            }
        }
        r.response = answer;
        r.event = key;
        return r;
    }
}