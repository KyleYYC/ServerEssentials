package me.kylesimmonds.serveressentials;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Scroll {
    private static final char COLOUR_CHAR = '§';
    private int position;
    private List<String> list;
    private ChatColor colour = ChatColor.RESET;

    /***
     * Creates a scroll instance
     * @param message - Message to be scrolled
     * @param width - Size of display
     * @param spaceBetween - Text break
     * @param colourChar - '&' or '§'
     */
    public Scroll(String message, int width, int spaceBetween, char colourChar) {
        list = new ArrayList<String>();

        if (message.length() < width) {
            StringBuilder sb = new StringBuilder(message);
            while (sb.length() < width)
                sb.append(" ");
            message = sb.toString();
        }

        width -= 2;

        if (width < 1)
            width = 1;
        if (spaceBetween < 0)
            spaceBetween = 0;

        //Support for both char types
        if (colourChar != '§')
            message = ChatColor.translateAlternateColorCodes(colourChar, message);

        for (int i = 0; i < message.length() - width; i++)
            list.add(message.substring(i, i + width));

        // Add scroller break
        StringBuilder space = new StringBuilder();
        for (int i = 0; i < spaceBetween; ++i) {
            list.add(message.substring(message.length() - width + (i > width ? width : i)) + space);
            if (space.length() < width)
                space.append(" ");
        }

        for (int i = 0; i < width - spaceBetween; ++i)
            list.add(message.substring(message.length() - width + spaceBetween + i) + space + message.substring(0, i));

        for (int i = 0; i < spaceBetween; i++) {
            if (i > space.length())
                break;
            list.add(space.substring(0, space.length() - i) + message.substring(0, width - (spaceBetween > width ? width : spaceBetween) + i));
        }
    }

    /**
     * @return Gets the next String to display
     */
    public String next() {
        StringBuilder sb = getNext();
        if (sb.charAt(sb.length() - 1) == COLOUR_CHAR)
            sb.setCharAt(sb.length() - 1, ' ');

        if (sb.charAt(0) == COLOUR_CHAR) {
            ChatColor c = ChatColor.getByChar(sb.charAt(1));
            if (c != null) {
                colour = c;
                sb = getNext();
                if (sb.charAt(0) != ' ')
                    sb.setCharAt(0, ' ');
            }
        }
        return colour + sb.toString();
    }

    private StringBuilder getNext() {
        return new StringBuilder(list.get(position++ % list.size()));
    }
}
