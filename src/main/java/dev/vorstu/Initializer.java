package dev.vorstu;

import dev.vorstu.db.entities.auth.AuthUserEntity;
import dev.vorstu.db.entities.films.Film;
import dev.vorstu.db.entities.films.Genre;
import dev.vorstu.db.entities.reviews.Comment;
import dev.vorstu.db.entities.reviews.Review;
import dev.vorstu.db.enums.ReviewStatus;
import dev.vorstu.db.enums.RoleUser;
import dev.vorstu.db.repositories.*;
import dev.vorstu.db.services.MinioService;
import dev.vorstu.db.services.films.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class Initializer {
    @Autowired
    private AuthUserRepository authUserRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MinioService minioService;
    @Autowired
    private FilmService filmService;

    public void initial() throws IOException {

        // create users
        AuthUserEntity admin = new AuthUserEntity("admin", "1234", "Matvey", "Kolmakov", RoleUser.SUPER_USER);
        authUserRepository.save(admin);
        AuthUserEntity user1 = new AuthUserEntity("user1", "12345678", "Andrey", "Korolev", RoleUser.USER);
        authUserRepository.save(user1);
        AuthUserEntity user2 = new AuthUserEntity("user2", "12345678", "Nikolay", "Pistcov", RoleUser.USER);
        authUserRepository.save(user2);
        AuthUserEntity admin1 = new AuthUserEntity("admin1", "1234", "Maria", "Kovalenko", RoleUser.SUPER_USER);
        authUserRepository.save(admin1);


        // create genres
        Genre drama = new Genre("драма");
        Genre biography = new Genre("биография");
        Genre fantastic = new Genre("фантастика");
        Genre actionFilm = new Genre("боевик");
        Genre horror  = new Genre("ужасы");
        Genre melodrama = new Genre("мелодрама");
        genreRepository.saveAll(Arrays.asList(drama, biography, fantastic, actionFilm, horror, melodrama));

        // create films
        Film oppenheimer = new Film("Оппенгеймер", 2023, 180, "История жизни американского физика-теоретика Роберта Оппенгеймера, который во времена Второй мировой войны руководил Манхэттенским проектом — секретными разработками ядерного оружия.",
                "США", biography.getId());
        filmRepository.save(oppenheimer);

        Film interstellar = new Film("Интерстеллар", 2014, 169, "Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.",
                "США", fantastic.getId());
        filmRepository.save(interstellar);
        Film prideAndPrejudice = new Film("Гордость и предубеждение", 2005, 129, "Англия, конец XVIII века. Родители пятерых сестер Беннет озабочены тем, чтобы удачно выдать дочерей замуж. И потому размеренная жизнь солидного семейства переворачивается вверх дном, когда по соседству появляется молодой джентльмен - мистер Бингли... Само собой, среди друзей нового соседа оказывается немало утонченных аристократов, которые не прочь поухаживать за очаровательными сестрами. Однако, все не так просто. Своевольная Элизабет знакомится с другом Бингли - красивым и высокомерным мистером Дарси...",
                "Великобритания", melodrama.getId());
        filmRepository.save(prideAndPrejudice);
        Film withloveRosie = new Film("С любовью, Рози", 2014, 102, "Рози и Алекс были лучшими друзьями с детства, и теперь, по окончании школы, собираются вместе продолжить учёбу в университете. Однако в их судьбах происходит резкий поворот, когда после ночи со звездой школы Рози узнаёт, что у неё будет ребенок. Невзирая на то, что обстоятельства и жизненные ситуации разлучают героев, они и спустя годы продолжают помнить друг о друге и о том чувстве, что соединило их в юности…",
                "Великобритания", melodrama.getId());
        filmRepository.save(withloveRosie);
        Film oneDay = new Film("Один день", 2011, 103, "Эмма романтична, остра на язык и хочет изменить мир к лучшему. Декстер — плейбой, баловень судьбы и хочет, чтобы мир принадлежал ему. Впервые они встретились на выпускном в колледже и провели вместе только один день. А потом пришла ночь, и они решили остаться друзьями.",
                "США", melodrama.getId());
        filmRepository.save(oneDay);
        Film littleWomen = new Film("Маленькие женщины", 2019, 135, "История взросления четырёх непохожих друг на друга сестер. Где-то бушует Гражданская война, но проблемы, с которыми сталкиваются девушки, актуальны как никогда: первая любовь, горькое разочарование, томительная разлука и непростые поиски себя и своего места в жизни.",
                "США", melodrama.getId());
        filmRepository.save(littleWomen);
        Film after = new Film("После", 2019, 105, "Случайная встреча перевернула их привычный мир. Она – прилежная студентка и образцовая дочь, а он – притягательный бунтарь с непростым прошлым. Живя в параллельных вселенных, они бы вряд ли даже взглянули друг на друга. Однако этому знакомству суждено разделить жизнь влюбленных на до и после.",
                "США", melodrama.getId());
        filmRepository.save(after);
        Film theGreenBook = new Film("Зелёная книга", 2018, 130, "1960-е годы. После закрытия нью-йоркского ночного клуба на ремонт вышибала Тони по прозвищу Болтун ищет подработку на пару месяцев. Как раз в это время Дон Ширли — утонченный светский лев, богатый и талантливый чернокожий музыкант, исполняющий классическую музыку — собирается в турне по южным штатам, где ещё сильны расистские убеждения и царит сегрегация. Он нанимает Тони в качестве водителя, телохранителя и человека, способного решать текущие проблемы. У этих двоих так мало общего, и эта поездка навсегда изменит жизнь обоих.",
                "США", biography.getId());
        filmRepository.save(theGreenBook);
        Film fordVsFerrari = new Film("Ford против Ferrari", 2019, 152, "В начале 1960-х Генри Форд II принимает решение улучшить имидж компании и сменить курс на производство более модных автомобилей. После неудавшейся попытки купить практически банкрота Ferrari американцы решают бросить вызов итальянским конкурентам на трассе и выиграть престижную гонку 24 часа Ле-Мана. Чтобы создать подходящую машину, компания нанимает автоконструктора Кэррола Шэлби, а тот отказывается работать без выдающегося, но, как считается, трудного в общении гонщика Кена Майлза. Вместе они принимаются за разработку впоследствии знаменитого спорткара Ford GT40.",
                "США", biography.getId());
        filmRepository.save(fordVsFerrari);
        Film theImitationGame = new Film("Игра в имитацию", 2014, 114, "Действие фильма происходит в 1941 году. Английскому математику Алану Тьюрингу* поручено расшифровать секретный код Третьего рейха «Энигма». Тьюринг действует по поручению британской разведки в центре, специализирующемся на взломе шифров и кодов. Для выполнения сверхсекретного задания здесь собрана целая команда талантливых учёных, которым предстоит создать первый в мире компьютер.",
                "Великобритания", biography.getId());
        filmRepository.save(theImitationGame);
        Film theMoorish = new Film("Мавританец", 2021, 129, "Не получив официальных обвинений и возможности защиты в суде, Мохаммед Ульд Слахи провел в тюрьме Гуантанамо более 6 лет. Только после этого он был удостоен права иметь адвокатов. Но и им в борьбе с правительственной машиной предстоит побороть личные сомнения.",
                "Великобритания", biography.getId());
        filmRepository.save(theMoorish);
        Film legendNo17 = new Film("Легенда №17", 2013, 134, "Это история становления великого советского хоккеиста, чемпиона Валерия Харламова, выступавшего в команде ЦСКА и в сборной СССР под номером 17. Повествование о том, как закалялся характер, как амбиции и честолюбие переплавились в высокое мастерство профессионала и человеческую мудрость. В сюжет тонко вплетается и личная жизнь героя, его отношения с родителями, сестрой, будущей супругой.",
                "Россия", biography.getId());
        filmRepository.save(legendNo17);
        Film theWolfOfWallStreet = new Film("Волк с Уолл-стрит", 2013, 180, "1987 год. Джордан Белфорт становится брокером в успешном инвестиционном банке. Вскоре банк закрывается после внезапного обвала индекса Доу-Джонса. По совету жены Терезы Джордан устраивается в небольшое заведение, занимающееся мелкими акциями. Его настойчивый стиль общения с клиентами и врождённая харизма быстро даёт свои плоды. Он знакомится с соседом по дому Донни, торговцем, который сразу находит общий язык с Джорданом и решает открыть с ним собственную фирму...",
                "США", drama.getId());
        filmRepository.save(theWolfOfWallStreet);
        Film onePlusOne = new Film("1+1", 2011, 112, "Пострадав в результате несчастного случая, богатый аристократ Филипп нанимает в помощники человека, который менее всего подходит для этой работы, – молодого жителя предместья Дрисса, только что освободившегося из тюрьмы. Несмотря на то, что Филипп прикован к инвалидному креслу, Дриссу удается привнести в размеренную жизнь аристократа дух приключений.",
                "Франция", drama.getId());
        filmRepository.save(onePlusOne);
        Film theStarsAreToBlame = new Film("Виноваты звёзды", 2014, 133, "Хэйзел больна раком. Несмотря на то, что болезнь временно отступила, девушка не чувствует ни капли радости. Она ходит в группу поддержки, где однажды знакомится с Огастусом Уотерсом и моментально влюбляется в него. Огастус и Хэйзел отправляются в полное страсти и жизни путешествие, которое лишний раз покажет им, что весь смысл жизни можно найти в любом ее отрезке.",
                "США", drama.getId());
        filmRepository.save(theStarsAreToBlame);
        Film janeAusten = new Film("Джейн Остин", 2007, 120, "Джейн Остин верит в любовь, а ее родители хотят, чтобы она вышла замуж по расчету: в Англии 1795 года у молодой женщины не было иного выбора. Однако, когда двадцатилетняя Джейн познакомилась с обаятельным молодым ирландцем Томом Лефроем, его ум и дерзость разожгли ее любопытство, и весь мир полетел кувырком. Сможет ли Джейн отвергнуть ухаживания племянника леди Гришем, пойти наперекор воле родителей и бросить вызов общественным устоям?",
                "Великобритания", drama.getId());
        filmRepository.save(janeAusten);
        Film theDevilWearsPrada = new Film("Дьявол носит Prada", 2006, 109, "Мечтающая стать журналисткой провинциальная девушка Энди по окончании университета получает должность помощницы всесильной Миранды Пристли, деспотичного редактора одного из крупнейших нью-йоркских журналов мод. Энди всегда мечтала о такой работе, не зная, с каким нервным напряжением это будет связано…",
                "США", drama.getId());
        filmRepository.save(theDevilWearsPrada);
        Film avatar = new Film("Аватар", 2009, 162, "Бывший морпех Джейк Салли прикован к инвалидному креслу. Несмотря на немощное тело, Джейк в душе по-прежнему остается воином. Он получает задание совершить путешествие в несколько световых лет к базе землян на планете Пандора, где корпорации добывают редкий минерал, имеющий огромное значение для выхода Земли из энергетического кризиса.",
                "США", fantastic.getId());
        filmRepository.save(avatar);
        Film alitaBattleAngel = new Film("Алита: Боевой ангел", 2019, 121, "300 лет после Великой войны, XXVI век. Доктор Идо находит останки женщины-киборга. После починки киборг ничего не помнит, но обнаруживает, что в состоянии пользоваться боевыми приемами. Начинаются поиски утерянных воспоминаний.",
                "США", fantastic.getId());
        filmRepository.save(alitaBattleAngel);
        Film divergent = new Film("Дивергент", 2014, 134, "В антиутопическом Чикаго будущего существует общество, члены которого придумали способ избегать конфликтов и поддерживать вокруг незыблемый порядок. Каждый человек по достижении 16 лет должен определить, к чему лежит его душа, и в зависимости от своих личностных качеств присоединиться к одной из пяти фракций – Искренность, Бесстрашие, Эрудиция, Дружелюбие или Отречение. Для того, чтобы и не ошибиться с фракцией, накануне церемонии выбора подростки проходят специальное тестирование. Юная Беатрис оказывается угрозой для всей сложившейся системы.",
                "США", fantastic.getId());
        filmRepository.save(divergent);
        Film theBlackWidow = new Film("Чёрная Вдова", 2021, 134, "Наташе Романофф предстоит лицом к лицу встретиться со своим прошлым. Чёрной Вдове придется вспомнить о том, что было в её жизни задолго до присоединения к команде Мстителей, и узнать об опасном заговоре, в который оказываются втянуты её старые знакомые — Елена, Алексей (известный как Красный Страж) и Мелина.",
                "США", fantastic.getId());
        filmRepository.save(theBlackWidow);
        Film gravity = new Film("Гравитация", 2013, 90, "Доктор Райан Стоун, блестящий специалист в области медицинского инжиниринга, отправляется в свою первую космическую миссию под командованием ветерана астронавтики Мэтта Ковальски, для которого этот полет - последний перед отставкой. Но во время, казалось бы, рутинной работы за бортом случается катастрофа. Шаттл уничтожен, а Стоун и Ковальски остаются совершенно одни; они находятся в связке друг с другом, и все, что они могут, - это двигаться по орбите в абсолютно черном пространстве без всякой связи с Землей и какой-либо надежды на спасение.",
                "США", fantastic.getId());
        filmRepository.save(gravity);
        Film humanAnger = new Film("Гнев человеческий", 2021, 119, "Грузовики лос-анджелесской инкассаторской компании Fortico Security часто подвергаются нападениям, и во время очередного ограбления погибают оба охранника. Через некоторое время в компанию устраивается крепкий немногословный британец Патрик Хилл. Он получает от босса прозвище Эйч и, впритык к необходимому минимуму пройдя тесты по фитнесу, стрельбе и вождению, отправляется на первое задание. Вскоре и его грузовик пытаются ограбить вооруженные налётчики, но Эйч в одиночку расправляется с целой бандой и становится героем.",
                "Великобритания", actionFilm.getId());
        filmRepository.save(humanAnger);
        Film warOfTheWorldsZ = new Film("Война миров Z", 2013, 116, "Бывший сотрудник ООН Джерри Лэйн оказывается в эпицентре эпидемии неизвестного вируса, который за считанные секунды превращает людей в зомби. Пытаясь найти противоядие против вируса, Лэйн путешествует вместе со своей группой почти по всему миру, поражённому эпидемией. Теперь судьба всего мира висит на волоске, и Джерри — его единственная надежда.",
                "США", actionFilm.getId());
        filmRepository.save(warOfTheWorldsZ);
        Film mrAndMrsSmith = new Film("Мистер и миссис Смит", 2005, 120, "Джон и Джейн женаты не так давно, но уже устали от брака. Им кажется, что они знают друг о друге всё. Но есть кое-что, что каждый предпочитает держать при себе: оба они – наёмные убийцы, которые тайно путешествуют по миру, выполняя опасные миссии. Эти отдельные тайные приключения становятся их общей судьбой в тот момент, когда Джейн получает заказ на Джона, а Джон - на Джейн.",
                "США", actionFilm.getId());
        filmRepository.save(mrAndMrsSmith);
        Film TheBodyguardISaKiller = new Film("Телохранитель киллера", 2017, 118, "Он — телохранитель мирового уровня. Его новая работа — охранять киллера, которого все мечтают убить. Он и сам с удовольствием прикончил бы этого гада, но работа есть работа: смертельные враги в прошлом, они вынуждены объединиться в настоящем. Однако их методы настолько различны, а принципы противоположны, так что вопрос выживания — под большим вопросом.",
                "США", actionFilm.getId());
        filmRepository.save(TheBodyguardISaKiller);
        Film TheDeadpool = new Film("Дэдпул", 2016, 108, "Уэйд Уилсон — наёмник. Будучи побочным продуктом программы вооружённых сил под названием «Оружие X», Уилсон приобрёл невероятную силу, проворство и способность к исцелению. Но страшной ценой: его клеточная структура постоянно меняется, а здравомыслие сомнительно. Всё, чего хочет Уилсон, — держаться на плаву в социальной выгребной яме. Но течение в ней слишком быстрое.",
                "США", actionFilm.getId());
        filmRepository.save(TheDeadpool);
        Film busanTrain = new Film("Поезд в Пусан", 2016, 118, "У маленькой Су-ан день рождения. Девочка живет с отцом в Сеуле и очень хочет отправиться к маме в Пусан. По дороге случается непредвиденное, и на страну обрушивается загадочный вирус. Пассажирам поезда в Пусан — единственного города, отразившего атаки вируса — придется бороться за выживание. 442 километра в пути. Добро пожаловать на борт и помните — в этой гонке недостаточно выжить, чтобы остаться человеком.",
                "Республика Корея", horror.getId());
        filmRepository.save(busanTrain);
        Film mirrors = new Film("Зеркала", 2008, 110, "Бывший нью-йоркский полицейский устраивается на работу ночным сторожем выгоревших руин крупного универмага. Обходя обугленные развалины, он начинает замечать что-то зловещее в декоративных зеркалах, украшающих стены: в огромных мерцающих стёклах отражается что-то жуткое.",
                "США", horror.getId());
        filmRepository.save(mirrors);
        Film theSpell = new Film("Заклятие", 2013, 112, "Эд и Лоррейн Уоррены — детективы, которые расследуют паранормальные дела. Однажды к ним обращается семья, страдающая от злого духа. Уоррены, вынужденные сражаться с могущественной демонической сущностью, сталкиваются с самым пугающим случаем в своей жизни.",
                "США", horror.getId());
        filmRepository.save(theSpell);
        Film theCurseOfTheNun = new Film("Проклятие монахини", 2018, 96, "Румыния, 1952 год. Когда в уединенном монастыре молодая монахиня совершает самоубийство, Ватикан отправляет расследовать происшествие священника с туманным прошлым и послушницу на пороге невозвратных обетов. Рискуя не только жизнью, но и душой, они сталкиваются со злобной силой, принявшей облик демонической монахини.",
                "США", horror.getId());
        filmRepository.save(theCurseOfTheNun);
        Film theRing = new Film("Звонок", 2002, 115, "Телефонный звонок раздаётся после просмотра некой загадочной видеокассеты. Жертве даётся ровно семь дней, а после следует неминуемая смерть. Журналистка Рэйчел расследует загадочные смерти молодых людей, и случайно кассета-убийца попадает в руки к её маленькому сыну, над которым теперь нависает смертельная угроза. У женщины есть только семь дней, чтобы разобраться в происходящем и спасти своего мальчика.",
                "США", horror.getId());
        filmRepository.save(theRing);


        // Create and save media for film1
        String[] fileNamesImage1 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/oppenheimer.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/oppenheimer1.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/oppenheimer2.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/oppenheimer3.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/oppenheimer4.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/oppenheimer5.png",
        };
        String[] fileNamesVideo1 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/oppenheimer.mp4",
        };
        createAndSaveMedia(oppenheimer, fileNamesImage1, fileNamesVideo1);
        String[] fileNamesImage2 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/interstellar.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/interstellar1.png",
        };
        String[] fileNamesVideo2 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/interstellar.mp4",
        };
        createAndSaveMedia(interstellar, fileNamesImage2, fileNamesVideo2);
        String[] fileNamesImage3 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/prideAndPrejudice.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/prideAndPrejudice1.png",
        };
        String[] fileNamesVideo3 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/prideAndPrejudice.mp4",
        };
        createAndSaveMedia(prideAndPrejudice, fileNamesImage3, fileNamesVideo3);
        String[] fileNamesImage4 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/theGreenBook.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theGreenBook1.png",
        };
        String[] fileNamesVideo4 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/theGreenBook.mp4",
        };
        createAndSaveMedia(theGreenBook, fileNamesImage4, fileNamesVideo4);
        String[] fileNamesImage5 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/withloveRosie.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/withloveRosie1.png",
        };
        String[] fileNamesVideo5 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/withloveRosie.mp4",
        };
        createAndSaveMedia(withloveRosie , fileNamesImage5, fileNamesVideo5);
        String[] fileNamesImage6 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/oneDay.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/oneDay1.png",
        };
        String[] fileNamesVideo6 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/oneDay.mp4",
        };
        createAndSaveMedia(oneDay, fileNamesImage6, fileNamesVideo6);
        String[] fileNamesImage7 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/after.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/after1.png",
        };
        String[] fileNamesVideo7 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/after.mp4",
        };
        createAndSaveMedia(after, fileNamesImage7, fileNamesVideo7);
        String[] fileNamesImage8 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/fordVsFerrari.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/fordVsFerrari1.png",
        };
        String[] fileNamesVideo8 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/fordVsFerrari.mp4",
        };
        createAndSaveMedia(fordVsFerrari, fileNamesImage8, fileNamesVideo8);
        String[] fileNamesImage9 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/littleWomen.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/littleWomen1.png",
        };
        String[] fileNamesVideo9 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/littleWomen.mp4",
        };
        createAndSaveMedia(littleWomen, fileNamesImage9, fileNamesVideo9);
        String[] fileNamesImage10 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/onePlusOne.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/onePlusOne1.png",
        };
        String[] fileNamesVideo10 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/onePlusOne.mp4",
        };
        createAndSaveMedia(onePlusOne, fileNamesImage10, fileNamesVideo10);
        String[] fileNamesImage11 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/theMoorish.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theMoorish1.png",
        };
        String[] fileNamesVideo11 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/theMoorish.mp4",
        };
        createAndSaveMedia(theMoorish, fileNamesImage11, fileNamesVideo11);
        String[] fileNamesImage12 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/theImitationGame.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theImitationGame1.png",
        };
        String[] fileNamesVideo12 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/theImitationGame.mp4",
        };
        createAndSaveMedia(theImitationGame, fileNamesImage12, fileNamesVideo12);
        String[] fileNamesImage13 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/theWolfOfWallStreet.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theWolfOfWallStreet1.png",
        };
        String[] fileNamesVideo13 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/theWolfOfWallStreet.mp4",
        };
        createAndSaveMedia(theWolfOfWallStreet, fileNamesImage13, fileNamesVideo13);
        String[] fileNamesImage14 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/legendNo17.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/legendNo171.png",
        };
        String[] fileNamesVideo14 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/legendNo17.mp4",
        };
        createAndSaveMedia(legendNo17, fileNamesImage14, fileNamesVideo14);
        String[] fileNamesImage15 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/theBlackWidow.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theBlackWidow1.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theBlackWidow2.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theBlackWidow3.png",
        };
        String[] fileNamesVideo15 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/theBlackWidow.mp4",
        };
        createAndSaveMedia(theBlackWidow, fileNamesImage15, fileNamesVideo15);
        String[] fileNamesImage16 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/theStarsAreToBlame.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theStarsAreToBlame1.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theStarsAreToBlame2.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theStarsAreToBlame3.png",
        };
        String[] fileNamesVideo16 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/theStarsAreToBlame.mp4",
        };
        createAndSaveMedia(theStarsAreToBlame, fileNamesImage16, fileNamesVideo16);
        String[] fileNamesImage17 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/janeAusten.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/janeAusten1.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/janeAusten2.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/janeAusten3.png",
        };
        String[] fileNamesVideo17 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/janeAusten.mp4",
        };
        createAndSaveMedia(janeAusten, fileNamesImage17, fileNamesVideo17);
        String[] fileNamesImage18 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/theDevilWearsPrada.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theDevilWearsPrada1.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theDevilWearsPrada2.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theDevilWearsPrada3.png",
        };
        String[] fileNamesVideo18 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/theDevilWearsPrada.mp4",
        };
        createAndSaveMedia(theDevilWearsPrada, fileNamesImage18, fileNamesVideo18);
        String[] fileNamesImage19 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/avatar.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/avatar1.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/avatar2.png",
        };
        String[] fileNamesVideo19 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/avatar.mp4",
        };
        createAndSaveMedia(avatar, fileNamesImage19, fileNamesVideo19);
        String[] fileNamesImage20 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/alitaBattleAngel.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/alitaBattleAngel1.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/alitaBattleAngel2.png",
        };
        String[] fileNamesVideo20 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/alitaBattleAngel.mp4",
        };
        createAndSaveMedia(alitaBattleAngel, fileNamesImage20, fileNamesVideo20);
        String[] fileNamesImage21 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/divergent.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/divergent1.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/divergent2.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/divergent3.png",
        };
        String[] fileNamesVideo21 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/divergent.mp4",
        };
        createAndSaveMedia(divergent, fileNamesImage21, fileNamesVideo21);
        String[] fileNamesImage22 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/mrAndMrsSmith.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/mrAndMrsSmith1.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/mrAndMrsSmith2.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/mrAndMrsSmith3.png",
        };
        String[] fileNamesVideo22 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/mrAndMrsSmith.mp4",
        };
        createAndSaveMedia(mrAndMrsSmith, fileNamesImage22, fileNamesVideo22);
        String[] fileNamesImage23 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/warOfTheWorldsZ.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/warOfTheWorldsZ1.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/warOfTheWorldsZ2.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/warOfTheWorldsZ3.png",
        };
        String[] fileNamesVideo23 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/warOfTheWorldsZ.mp4",
        };
        createAndSaveMedia(warOfTheWorldsZ, fileNamesImage23, fileNamesVideo23);
        String[] fileNamesImage24 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/humanAnger.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/humanAnger1.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/humanAnger2.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/humanAnger3.png",
        };
        String[] fileNamesVideo24 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/humanAnger.mp4",
        };
        createAndSaveMedia(humanAnger, fileNamesImage24, fileNamesVideo24);
        String[] fileNamesImage25 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/gravity.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/gravity1.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/gravity2.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/gravity3.png",
        };
        String[] fileNamesVideo25 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/gravity.mp4",
        };

        createAndSaveMedia(gravity, fileNamesImage25, fileNamesVideo25);
        String[] fileNamesImage26 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/theSpell.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theSpell1.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theSpell2.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theSpell3.png",
        };
        String[] fileNamesVideo26 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/theSpell.mp4",
        };
        createAndSaveMedia(theSpell, fileNamesImage26, fileNamesVideo26);
        String[] fileNamesImage27 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/theCurseOfTheNun.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theCurseOfTheNun1.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theCurseOfTheNun2.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theCurseOfTheNun3.png",
        };
        String[] fileNamesVideo27 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/theCurseOfTheNun.mp4",
        };
        createAndSaveMedia(theCurseOfTheNun, fileNamesImage27, fileNamesVideo27);
        String[] fileNamesImage28 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/theRing.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theRing1.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theRing2.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/theRing3.png",
        };
        String[] fileNamesVideo28 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/theRing.mp4",
        };
        createAndSaveMedia(theRing, fileNamesImage28, fileNamesVideo28);
        String[] fileNamesImage29 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/mirrors.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/mirrors1.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/mirrors2.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/mirrors3.png",
        };
        String[] fileNamesVideo29 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/mirrors.mp4",
        };
        createAndSaveMedia(mirrors, fileNamesImage29, fileNamesVideo29);
        String[] fileNamesImage30 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/busanTrain.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/busanTrain1.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/busanTrain2.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/busanTrain3.png",
        };
        String[] fileNamesVideo30 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/busanTrain.mp4",
        };
        createAndSaveMedia(busanTrain, fileNamesImage30, fileNamesVideo30);
        String[] fileNamesImage31 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/TheDeadpool.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/TheDeadpool1.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/TheDeadpool2.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/TheDeadpool3.png",
        };
        String[] fileNamesVideo31 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/TheDeadpool.mp4",
        };
        createAndSaveMedia(TheDeadpool, fileNamesImage31, fileNamesVideo31);
        String[] fileNamesImage32 = {
                "C:/Users/matve/workspaceUniver/MinIO/images/TheBodyguardISaKiller.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/TheBodyguardISaKiller1.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/TheBodyguardISaKiller2.png",
                "C:/Users/matve/workspaceUniver/MinIO/images/TheBodyguardISaKiller3.png",
        };
        String[] fileNamesVideo32 = {
                "C:/Users/matve/workspaceUniver/MinIO/videos/TheBodyguardISaKiller.mp4",
        };
        createAndSaveMedia(TheBodyguardISaKiller, fileNamesImage32, fileNamesVideo32);


        // create reviews
        Review reviewForFilm1 = new Review(prideAndPrejudice.getId(), admin1.getId(),"Призрак Остин бродит по Дербиширу.","Спустя 10 лет англичане решили снять новую версию бессмертного романа Джейн Остин «Гордость и предубеждение». Многие знают сюжет этого умного, тонкого, наполненного чудесным юмором произведения, если кто не в курсе, напомним его. Как начинаются многие русские народные сказки «И было у отца три сына: двое умных, а третий был дурак». Остин же сразу ставит все точки над i, и процентное соотношение скажем так, не совсем умных героев у нее превосходит предел в 50. Итак, в славном, тихом, провинциальном городке Меритоне проживает семья, возглавляемая папой - дворянином, мамой – представительницей среднего класса и пятью дочками. Вот именно женское население этой семьи страдает от недостаточного количества серого вещества. А именно мама и три младшие дочери, а на семью из семи человек это не мало. Конечно, все девушки мечтают о большой и чистой любви, и вскоре на их горизонте появляются два джентльмена из высшего общества, один из которых очень добродушный и открытый малый,",
                ReviewStatus.VERIFIED);
        reviewRepository.save(reviewForFilm1);
        Review reviewForFilm2 = new Review(oppenheimer.getId(), admin1.getId(),"Нолан — визуальный графоман","Вышел новый фильм главного хитмейкера Голливуда, но в этот раз всё не так плохо. Чтобы лучше понять что будет ниже — мне понравился “Престиж”, “Тёмный Рыцарь“ был не так плох, “Мементо” был середнячком, из “Начала” я помню всё визуальное, а “Интерстеллар” был тягомотиной, но с парой классных сцен. Вчера сходил на Оппенхаймер - байопик о проджект-менеджере атомной бомбы на три часа Фильм начинается быстро. Нолан вводит главного героя, прошаренного физика Роберта Оппенхаймера. Он тусуется в университете Бёркли и гредит о чём-то великом. Но крутость Оппенхаймера как физика нам не покажут. Главный герой как бы по умолчанию крут, и нам стоит только этому верить. Вот он говорит с человеком, вот уже с другим, а вот его клеит какая-то баба. Все его любят, но как бы постфактум",
                ReviewStatus.VERIFIED);
        reviewRepository.save(reviewForFilm2);
        Review reviewForFilm7 = new Review(oppenheimer.getId(), user2.getId(),"«Все всё поняли с первого раза»","Мое впечатление от фильма описывает выражение лица Киллиана Мерфи с постера. Оппенгеймер - это просто пересказ статьи из Википедии, обычный байопик с посылом, известным и так всему миру - ядерная война уничтожит планету. Выход этого фильма не приоткрывает никакой завесы, не дает ничего нового и не трогает до глубины сердца. Племена, далекие от цивилизации и интернета, никак не изменили бы свою жизнь, посмотрев данный фильм. Как обычно Нолан круто постарался над качеством картинки, постановкой кадра и кастингом. Актеры хорошие, правда не совсем понятны камео некоторых из них на пару минут экранного времени, но ладно. Чб фильтр, использованный для демонстрации другой временной линии, бесполезен - то, что кто-то путается при просмотре подобных фильмов - это их проблемы.",
                ReviewStatus.VERIFIED);
        reviewRepository.save(reviewForFilm7);

        Review reviewForFilm3 = new Review(oppenheimer.getId(), user1.getId(),"Тяга к неизведанному","Когда я впервые услышал о том, что Нолан снимает биографию «отца атомной бомбы», чувства были смешанные. С одной стороны сомнения: биография? После фантастических «Интерстеллара», «Начала», «Темного рыцаря», «Довода»? С другой стороны — манящая неизвестность: если уж мастер и взялся за такую сложную тему, результат не должен оставить равнодушным. «Оппенгеймер» — длинный фильм. Три часа — это примерно вдвое больше идеальной продолжительности фильма. Нолан не чурается хронометража, но даже для него 180 минут — ощутимый размах. Самой подвижной и визуально насыщенной части фильма, работам по созданию атомной бомбы и ее испытанию, посвящена треть экранного времени. Две трети — слушания по допуску Оппенгеймера к секретной работе, поиски затаившихся повсюду американских коммунистов, диалоги, диалоги, диалоги. «Оппенгеймер» — сложный фильм. Калейдоскоп лиц, событий, мест. Большое количество персонажей, занятых в важнейшем проекте первой половины ХХ века. Действие может перенести в несколько мест за пару минут: Нолан верен своей манере вести несколько сюжетных линий параллельно. Самые наблюдательные заметят разницу в формате кадра между сценами и смогут строить теории, что это значит. В три часа поместилось примерно 30 лет из жизни Оппенгеймера — от учебы в Кембридже в 1920-х до слушаний по допуску к секретной работе в 1954-м. «Оппенгеймер» — документальный фильм. Творческого домысла здесь нет: турбулентная история жизни знаменитого физика хорошо известна. Дьявол кроется в деталях: по-нолановски визуальной иллюстрации мыслей и страхов главного героя, до дрожи пробирающего ожидания первого испытания «Тринити» и воссоздании сметающего все на своем пути взрыва первой атомной бомбы.",
                ReviewStatus.VERIFIED);
        reviewRepository.save(reviewForFilm3);
        Review reviewForFilm6 = new Review(oppenheimer.getId(), user1.getId(),"Тяга к неизведанному","Когда я впервые услышал о том, что Нолан снимает биографию «отца атомной бомбы», чувства были смешанные. С одной стороны сомнения: биография? После фантастических «Интерстеллара», «Начала», «Темного рыцаря», «Довода»? С другой стороны — манящая неизвестность: если уж мастер и взялся за такую сложную тему, результат не должен оставить равнодушным. «Оппенгеймер» — длинный фильм. Три часа — это примерно вдвое больше идеальной продолжительности фильма. Нолан не чурается хронометража, но даже для него 180 минут — ощутимый размах. Самой подвижной и визуально насыщенной части фильма, работам по созданию атомной бомбы и ее испытанию, посвящена треть экранного времени. Две трети — слушания по допуску Оппенгеймера к секретной работе, поиски затаившихся повсюду американских коммунистов, диалоги, диалоги, диалоги. «Оппенгеймер» — сложный фильм. Калейдоскоп лиц, событий, мест. Большое количество персонажей, занятых в важнейшем проекте первой половины ХХ века. Действие может перенести в несколько мест за пару минут: Нолан верен своей манере вести несколько сюжетных линий параллельно. Самые наблюдательные заметят разницу в формате кадра между сценами и смогут строить теории, что это значит. В три часа поместилось примерно 30 лет из жизни Оппенгеймера — от учебы в Кембридже в 1920-х до слушаний по допуску к секретной работе в 1954-м. «Оппенгеймер» — документальный фильм. Творческого домысла здесь нет: турбулентная история жизни знаменитого физика хорошо известна. Дьявол кроется в деталях: по-нолановски визуальной иллюстрации мыслей и страхов главного героя, до дрожи пробирающего ожидания первого испытания «Тринити» и воссоздании сметающего все на своем пути взрыва первой атомной бомбы.",
                ReviewStatus.VERIFIED);
        reviewRepository.save(reviewForFilm6);
        Review reviewForFilm11 = new Review(oppenheimer.getId(), user1.getId(),"Тяга к неизведанному","Когда я впервые услышал о том, что Нолан снимает биографию «отца атомной бомбы», чувства были смешанные. С одной стороны сомнения: биография? После фантастических «Интерстеллара», «Начала», «Темного рыцаря», «Довода»? С другой стороны — манящая неизвестность: если уж мастер и взялся за такую сложную тему, результат не должен оставить равнодушным. «Оппенгеймер» — длинный фильм. Три часа — это примерно вдвое больше идеальной продолжительности фильма. Нолан не чурается хронометража, но даже для него 180 минут — ощутимый размах. Самой подвижной и визуально насыщенной части фильма, работам по созданию атомной бомбы и ее испытанию, посвящена треть экранного времени. Две трети — слушания по допуску Оппенгеймера к секретной работе, поиски затаившихся повсюду американских коммунистов, диалоги, диалоги, диалоги. «Оппенгеймер» — сложный фильм. Калейдоскоп лиц, событий, мест. Большое количество персонажей, занятых в важнейшем проекте первой половины ХХ века. Действие может перенести в несколько мест за пару минут: Нолан верен своей манере вести несколько сюжетных линий параллельно. Самые наблюдательные заметят разницу в формате кадра между сценами и смогут строить теории, что это значит. В три часа поместилось примерно 30 лет из жизни Оппенгеймера — от учебы в Кембридже в 1920-х до слушаний по допуску к секретной работе в 1954-м. «Оппенгеймер» — документальный фильм. Творческого домысла здесь нет: турбулентная история жизни знаменитого физика хорошо известна. Дьявол кроется в деталях: по-нолановски визуальной иллюстрации мыслей и страхов главного героя, до дрожи пробирающего ожидания первого испытания «Тринити» и воссоздании сметающего все на своем пути взрыва первой атомной бомбы.",
                ReviewStatus.VERIFIED);
        reviewRepository.save(reviewForFilm11);
        Review reviewForFilm8 = new Review(oppenheimer.getId(), user1.getId(),"Тяга к неизведанному","Когда я впервые услышал о том, что Нолан снимает биографию «отца атомной бомбы», чувства были смешанные. С одной стороны сомнения: биография? После фантастических «Интерстеллара», «Начала», «Темного рыцаря», «Довода»? С другой стороны — манящая неизвестность: если уж мастер и взялся за такую сложную тему, результат не должен оставить равнодушным. «Оппенгеймер» — длинный фильм. Три часа — это примерно вдвое больше идеальной продолжительности фильма. Нолан не чурается хронометража, но даже для него 180 минут — ощутимый размах. Самой подвижной и визуально насыщенной части фильма, работам по созданию атомной бомбы и ее испытанию, посвящена треть экранного времени. Две трети — слушания по допуску Оппенгеймера к секретной работе, поиски затаившихся повсюду американских коммунистов, диалоги, диалоги, диалоги. «Оппенгеймер» — сложный фильм. Калейдоскоп лиц, событий, мест. Большое количество персонажей, занятых в важнейшем проекте первой половины ХХ века. Действие может перенести в несколько мест за пару минут: Нолан верен своей манере вести несколько сюжетных линий параллельно. Самые наблюдательные заметят разницу в формате кадра между сценами и смогут строить теории, что это значит. В три часа поместилось примерно 30 лет из жизни Оппенгеймера — от учебы в Кембридже в 1920-х до слушаний по допуску к секретной работе в 1954-м. «Оппенгеймер» — документальный фильм. Творческого домысла здесь нет: турбулентная история жизни знаменитого физика хорошо известна. Дьявол кроется в деталях: по-нолановски визуальной иллюстрации мыслей и страхов главного героя, до дрожи пробирающего ожидания первого испытания «Тринити» и воссоздании сметающего все на своем пути взрыва первой атомной бомбы.",
                ReviewStatus.VERIFIED);
        reviewRepository.save(reviewForFilm8);
        Review reviewForFilm9 = new Review(oppenheimer.getId(), user1.getId(),"Тяга к неизведанному","Когда я впервые услышал о том, что Нолан снимает биографию «отца атомной бомбы», чувства были смешанные. С одной стороны сомнения: биография? После фантастических «Интерстеллара», «Начала», «Темного рыцаря», «Довода»? С другой стороны — манящая неизвестность: если уж мастер и взялся за такую сложную тему, результат не должен оставить равнодушным. «Оппенгеймер» — длинный фильм. Три часа — это примерно вдвое больше идеальной продолжительности фильма. Нолан не чурается хронометража, но даже для него 180 минут — ощутимый размах. Самой подвижной и визуально насыщенной части фильма, работам по созданию атомной бомбы и ее испытанию, посвящена треть экранного времени. Две трети — слушания по допуску Оппенгеймера к секретной работе, поиски затаившихся повсюду американских коммунистов, диалоги, диалоги, диалоги. «Оппенгеймер» — сложный фильм. Калейдоскоп лиц, событий, мест. Большое количество персонажей, занятых в важнейшем проекте первой половины ХХ века. Действие может перенести в несколько мест за пару минут: Нолан верен своей манере вести несколько сюжетных линий параллельно. Самые наблюдательные заметят разницу в формате кадра между сценами и смогут строить теории, что это значит. В три часа поместилось примерно 30 лет из жизни Оппенгеймера — от учебы в Кембридже в 1920-х до слушаний по допуску к секретной работе в 1954-м. «Оппенгеймер» — документальный фильм. Творческого домысла здесь нет: турбулентная история жизни знаменитого физика хорошо известна. Дьявол кроется в деталях: по-нолановски визуальной иллюстрации мыслей и страхов главного героя, до дрожи пробирающего ожидания первого испытания «Тринити» и воссоздании сметающего все на своем пути взрыва первой атомной бомбы.",
                ReviewStatus.VERIFIED);
        reviewRepository.save(reviewForFilm9);
        Review reviewForFilm10 = new Review(oppenheimer.getId(), user1.getId(),"Тяга к неизведанному","Когда я впервые услышал о том, что Нолан снимает биографию «отца атомной бомбы», чувства были смешанные. С одной стороны сомнения: биография? После фантастических «Интерстеллара», «Начала», «Темного рыцаря», «Довода»? С другой стороны — манящая неизвестность: если уж мастер и взялся за такую сложную тему, результат не должен оставить равнодушным. «Оппенгеймер» — длинный фильм. Три часа — это примерно вдвое больше идеальной продолжительности фильма. Нолан не чурается хронометража, но даже для него 180 минут — ощутимый размах. Самой подвижной и визуально насыщенной части фильма, работам по созданию атомной бомбы и ее испытанию, посвящена треть экранного времени. Две трети — слушания по допуску Оппенгеймера к секретной работе, поиски затаившихся повсюду американских коммунистов, диалоги, диалоги, диалоги. «Оппенгеймер» — сложный фильм. Калейдоскоп лиц, событий, мест. Большое количество персонажей, занятых в важнейшем проекте первой половины ХХ века. Действие может перенести в несколько мест за пару минут: Нолан верен своей манере вести несколько сюжетных линий параллельно. Самые наблюдательные заметят разницу в формате кадра между сценами и смогут строить теории, что это значит. В три часа поместилось примерно 30 лет из жизни Оппенгеймера — от учебы в Кембридже в 1920-х до слушаний по допуску к секретной работе в 1954-м. «Оппенгеймер» — документальный фильм. Творческого домысла здесь нет: турбулентная история жизни знаменитого физика хорошо известна. Дьявол кроется в деталях: по-нолановски визуальной иллюстрации мыслей и страхов главного героя, до дрожи пробирающего ожидания первого испытания «Тринити» и воссоздании сметающего все на своем пути взрыва первой атомной бомбы.",
                ReviewStatus.VERIFIED);
        reviewRepository.save(reviewForFilm10);

        Review reviewForFilm4 = new Review(interstellar.getId(), admin.getId(),"Черная дыра, как решение всех сюжетных проблем","Творческую свободу в кино нужно ограничивать. И этот трехчасовой псевдонаучный фильм-космоопера наглядный тому пример. Нолан почувствовал себя человеком, над которым никто не стоит и что его вседозволенность способна создать новую веху в жанре научной фантастики. К сожалению, последнего не произошло, а скорее наоборот. Фишкой почти всех фильмов Кристофера было усложнение простых вещей. У него есть действительно сильные фильмы, такие как «Бессонница» или «Помни». Теперь же он ступил туда, где ничего усложнять не нужно и где все и без него очень запутанно, относительно и многое непонятно. Попытка соединить художественный замысел с теорией гравитации, относительности и теорией о черных дырах во Вселенной похожа на то, как два магнита с одним полюсом пытаются соединиться. Безусловно, в фильме есть моменты, которые цепляют по-настоящему. Есть моменты с очень неожиданными поворотами, которые так же цепляют.",
                ReviewStatus.VERIFIED);
        reviewRepository.save(reviewForFilm4);
        Review reviewForFilm5 = new Review(interstellar.getId(), admin1.getId(),"Теория всего","Как же трудно, наверное, не сломаться под непомерным весом угрожающих амбиций, особенно когда речь идёт о 'самом ожидаемом фильме года' - именно так и никак иначе нарекли последнюю работу Кристофера Нолана в киноманской среде. Пользы от этой истерии мало. Не ждите 'всего, и сразу, и побольше', ведь блокбастер - последнее слово, которое приходит на ум при попытке как-то охарактеризовать 'Интерстеллар', поскольку интеллект здесь явно перевешивает даже такое шикарное внешнее оформление. Как бы это пошло не звучало, но перед нами действительно многогранный и сложный фильм, который можно адекватно воспринимать в нескольких различных плоскостях, и максимальный эффект будет достигнут только если вы будете способны заглянуть глубже. В недалёком будущем человечество находится на стадии регресса: высокие технологии нас уже не так заботят, как первостепенная потребность в пище.",
                ReviewStatus.VERIFIED);
        reviewRepository.save(reviewForFilm5);

        // create comments
        Comment commentForReview1 = new Comment(admin1.getId(),reviewForFilm1.getId(),"Первый комментарий");
        commentRepository.save(commentForReview1);
        Comment commentForReview2 = new Comment(admin1.getId(),reviewForFilm1.getId(),"Первый комментарий к первому комментарию", commentForReview1.getId());
        commentRepository.save(commentForReview2);
        Comment commentForReview3 = new Comment(admin1.getId(),reviewForFilm1.getId(),"второй комментарий ко первому комментарию",commentForReview1.getId());
        commentRepository.save(commentForReview3);
        Comment commentForReview7 = new Comment(admin1.getId(),reviewForFilm1.getId(),"первый комментарий к первому комментарию второго комментария",commentForReview2.getId());
        commentRepository.save(commentForReview7);

        Comment commentForReview4 = new Comment(admin1.getId(),reviewForFilm1.getId(),"второй комментарий");
        commentRepository.save(commentForReview4);
        Comment commentForReview5 = new Comment(admin1.getId(),reviewForFilm1.getId(),"первый комментарий ко второму комментарию",commentForReview4.getId());
        commentRepository.save(commentForReview5);
        Comment commentForReview6 = new Comment(admin1.getId(),reviewForFilm1.getId(),"второй комментарий ко второму комментарию",commentForReview4.getId());
        commentRepository.save(commentForReview6);

        Comment commentForReview8 = new Comment(admin1.getId(),reviewForFilm1.getId(),"третий комментарий");
        commentRepository.save(commentForReview8);



    }
    private MultipartFile createMultipartFile(String fileName) throws IOException {
        File file = new File(fileName);
        String mimeType = Files.probeContentType(Paths.get(file.getAbsolutePath()));
        byte[] fileBytes = StreamUtils.copyToByteArray(Files.newInputStream(file.toPath()));
        return new MockMultipartFile(file.getName(), file.getName(), mimeType, fileBytes);
    }
    private void createAndSaveMedia(Film film, String[] fileNamesImage, String[] fileNamesVideo) throws IOException {
        ArrayList<MultipartFile> images = new ArrayList<>();
        ArrayList<MultipartFile> videos = new ArrayList<>();
        for (String fileName : fileNamesImage) {
            images.add(createMultipartFile(fileName));
        }
        for (String fileName : fileNamesVideo) {
            videos.add(createMultipartFile(fileName));
        }
        String bucketNameFilms = film.getId().toString() + "-filmbucket";
        minioService.createBucket(bucketNameFilms);
        filmService.saveImages(images, bucketNameFilms, film);
        filmService.saveVideos(videos, bucketNameFilms, film);
    }

}
