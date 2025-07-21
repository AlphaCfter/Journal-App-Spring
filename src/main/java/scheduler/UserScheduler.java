package scheduler;

import com.alphacfter.journalApp.cache.AppCache;
import com.alphacfter.journalApp.entity.JournalEntry;
import com.alphacfter.journalApp.entity.User;
import com.alphacfter.journalApp.repository.UserRepositoryImpl;
import com.alphacfter.journalApp.service.EmailService;
import com.alphacfter.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendMail(){
        List<User> users = userRepository.getUsersForSentimentAnalysis();
        for(User user : users){
            List<JournalEntry> journalEntry = user.getJournalEntry();
            List<String> filter = journalEntry.stream()
                    .filter(x -> x.getDate()
                            .isAfter(LocalDateTime.now()
                                    .minus(7, ChronoUnit.DAYS))).map(x -> x.getContent()).collect(Collectors.toList());
            String sentiment = String.join("",filter);
            sentimentAnalysisService.getSentiment(sentiment);
            emailService.sendMail(user.getEmail(),"Sentiment for 7 days", sentiment);
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }
}
