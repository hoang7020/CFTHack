package ai.loko.hk.ui.discord;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class DiscordBot {
    public static final String TOKEN = "NTY3MTczODA3NDMzODQyNjk4.XLPyFQ.2K0aEUgLRURN9wTEN_r-5pcs80M";

    public static void activeBot(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                JDABuilder builder = new JDABuilder(AccountType.BOT);
                builder.setToken(TOKEN);
                builder.addEventListeners(adapter);
                JDA jda = null;
                try {
                    jda = builder.build();
                    jda.awaitReady();
                } catch (LoginException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    static ListenerAdapter adapter = new ListenerAdapter() {};
}
