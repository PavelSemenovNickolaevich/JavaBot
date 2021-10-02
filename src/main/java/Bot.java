import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.io.File;

public class Bot extends TelegramLongPollingBot {
    Book book = new Book();
    private long chat_id;

    @Override
    public String getBotUsername() {
        return "Iron_Viking_Bot";
    }

    @Override
    public String getBotToken() {
        return "1861058196:AAEqt3u2qzKmuV4Lo6CFCSb0VV4Mbvt36t8";
    }

    @Override
    public void onUpdateReceived(Update update) {
        chat_id = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage().setChatId(chat_id);

        sendMessage.setText(input(update.getMessage().getText()));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }


    private String input(String msg) {
        if (msg.contains("Hi") || msg.contains("Привет") || msg.contains("Privet") || msg.contains("Hello")) {
            return "Привет дружище";
        }
        return msg;
    }

    public String getInfoBook(){
        try {
            URL url = new URL(book.getImage());
            // берем сслыку на изображение
            BufferedImage img = ImageIO.read(url);
            // качаем изображение в буфер
            File outputfile = new File("image.jpg");
            //создаем новый файл в который поместим
            //скаченое изображение
            ImageIO.write(img, "jpg", outputfile);
            //преобразовуем наше буферное изображение
            //в новый файл
            SendPhoto sendPhoto = new SendPhoto().setChatId(chat_id);
            sendPhoto.setPhoto(outputfile);
            execute(sendPhoto);
        } catch (Exception e){
            System.out.println("File not found");
            e.printStackTrace();
        }

        String info = book.getTitle()
                + "\nАвтор" + book.getAutorName()
                + "\nЖанр" + book.getGenres()
                + "\n\nОписание\n" + book.getDescription()
                + "\n\nКоличество лайков " + book.getLikes()
                + "\n\nПоследние комментарии\n" + book.getCommentList();

        return info;
    }
}
