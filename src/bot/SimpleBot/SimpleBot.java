package bot.SimpleBot;

import java.io.*;
import java.util.*;
import java.util.regex.*;

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
            "Вопрос непростой, прошу тайм-аут на раздумья.",
            "Не уверен, что располагаю такой информацией.",
            "Может лучше поговорим о чём-то другом?",
            "Простите, но это очень личный вопрос.",
            "Не уверен, что Вам понравится ответ.",
            "Поверьте, я сам хотел бы это знать.",
            "Вы действительно хотите это знать?",
            "Уверен, Вы уже догадались сами.",
            "Зачем Вам такая информация?",
            "Давайте сохраним интригу?"};
    final Map<String, String> PATTERNS_FOR_ANALYSIS = new HashMap<String, String>() {{
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
        // iamfeelling
        put("кажется", "iamfeelling");
        put("чувствую", "iamfeelling");
        put("испытываю", "iamfeelling");
        // yes
        put("да", "yes");
        put("согласен", "yes");
        put("согласна", "yes");
        put("и правда", "yes");
        // whattime
        put("который\\s.*час", "whattime");
        put("сколько\\s.*времени", "whattime");
        // bye
        put("прощай", "bye");
        //put("пока", "bye");
        put("увидимся", "bye");
        put("до\\s.*свидания", "bye");
    }};

    final String[] HELLO = {
            "Привет!",
            "Здравствуй!",
            "Салют!",
            "И тебе привет!",
            "Вас там тентакли занесли ко мне?",
            "Как приятно тебя читать!",
            "Дарова",
            "Купи слона",
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
            "Все говорят да, а ты купи слона"};
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
        put("iamfeelling", IMFEELLING);
        put("yes", YES);
        put("bye", BYE);
        put("anecdote", JOKE);
        put("reaction", REACTION);
    }};
    Pattern pattern; // for regexp
    Random random; // for random answers

    public SimpleBot() {
        random = new Random();
    }

    public int countWordsInChat(String answer, String user_ID) throws FileNotFoundException, IOException {
        String directory = System.getProperty("user.dir");
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
        return countWords;
    }
    public String[] sayInReturn(String message, String user_ID) throws FileNotFoundException, IOException {
        String answer= "";
        String key = "common";
        if (message.equals("/help")) {
            String[] respond = {"Привет! Я бот Супа-Дупа. Мы можем с тобой поговорить о чём-нибудь, " +
                    "или я могу рассказать тебе анекдот.", "settings"};
            return respond;
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
                        String[] reply = {"Я рассказал все анекдоты((", "jokes are over"};
                        return reply;
                    }
                    String[] reply = {JOKE[countAnecdotes], "anecdote"};
                    return reply;
                }
                String say[] = ANSWERS_BY_PATTERNS.get(o.getValue());
                String[] reply = {say[random.nextInt(say.length)], o.getValue()};
                return reply;

            }
        }
        String[] respond = {answer, key};
        return respond;
    }
}
