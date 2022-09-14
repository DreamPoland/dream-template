package cc.dreamcode.template.features.notice;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import fr.mrmicky.fastboard.FastBoard;
import jdk.tools.jlink.plugin.PluginException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * NoticeSender is interface for sending formatted messages to player.
 * Notice allows you to choose how the message are displayed and format them automatically. (+ config)
 */
public interface NoticeSender {
    default void send(Notice notice, Player player, String text) {
        switch (notice.getType()) {
            case CHAT: {
                String[] split = text.split("%NEWLINE%");
                Arrays.stream(split).forEach(player::sendMessage);
                break;
            }
            case ACTION_BAR: {
                ActionBar.sendActionBar(player, text.replace("%NEWLINE%", ""));
                break;
            }
            case TITLE: {
                Titles.sendTitle(player, text.replace("%NEWLINE%", ""), " ");
                break;
            }
            case SUBTITLE: {
                Titles.sendTitle(player, " ", text.replace("%NEWLINE%", ""));
                break;
            }
            case TITLE_SUBTITLE: {
                String[] split = text.split("%NEWLINE%");
                if (split.length == 0) {
                    throw new PluginException("Notice with TITLE_SUBTITLE need have %NEWLINE% to include title with subtitle.");
                }
                Titles.sendTitle(player, split[0], split[1]);
                break;
            }
            case SIDEBAR: {
                final FastBoard fastBoard = new FastBoard(player);
                String[] split = text.split("%NEWLINE%");
                Arrays.stream(split).forEach(fastBoard::updateTitle);

                final Timer timer = new Timer();
                final TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        if (fastBoard.isDeleted()) {
                            return;
                        }
                        fastBoard.delete();
                    }
                };

                timer.schedule(task, notice.getDuration() * 50L);
                break;
            }
            default: {
                String[] split = text.split("%NEWLINE%");
                Arrays.stream(split).forEach(player::sendMessage);
            }
        }
    }

    default void send(Notice notice, Player player) {
        this.send(notice, player, IridiumColorAPI.process(notice.getText()));
    }

    default void send(Notice notice, CommandSender commandSender) {
        if (commandSender instanceof Player) {
            this.send(notice, (Player) commandSender);
            return;
        }

        commandSender.sendMessage(notice.getColoredText());
    }

    default void send(Notice notice, Collection<Player> players) {
        players.forEach(player -> this.send(notice, player));
    }

    default void send(Notice notice, Player player, Map<String, Object> mapReplacer) {
        this.send(notice, player, IridiumColorAPI.process(notice.getContext().with(mapReplacer).apply()));
    }

    default void send(Notice notice, CommandSender commandSender, Map<String, Object> mapReplacer) {
        if (commandSender instanceof Player) {
            this.send(notice, (Player) commandSender, mapReplacer);
            return;
        }

        commandSender.sendMessage(notice.getContext().with(mapReplacer).apply());
    }

    default void send(Notice notice, Collection<Player> players, Map<String, Object> mapReplacer) {
        players.forEach(player -> this.send(notice, player, mapReplacer));
    }

}
