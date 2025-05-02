package com.apcs.disunity.input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Contains all input types from all sources
 * 
 * @author Qinzhao Li
 */
public enum Input {

    /* ================ [ VALUES ] ================ */

    // Mouse buttons
    MOUSE_LEFT("Left Click"),
    MOUSE_RIGHT("Right Click"),
    MOUSE_MIDDLE("Middle Click"),
    
    // Keyboard buttons
    KEY_ENTER("Enter"),
    KEY_BACK_SPACE("Backspace"),
    KEY_TAB("Tab"),
    KEY_CANCEL("Cancel"),
    KEY_CLEAR("Clear"),
    KEY_SHIFT("Shift"),
    KEY_CONTROL("Control"),
    KEY_ALT("Alt"),
    KEY_PAUSE("Pause"),
    KEY_CAPS_LOCK("Caps Lock"),
    KEY_ESCAPE("Escape"),
    KEY_SPACE("Space"),
    KEY_PAGE_UP("Page Up"),
    KEY_PAGE_DOWN("Page Down"),
    KEY_END("End"),
    KEY_HOME("Home"),
    KEY_LEFT("Left"),
    KEY_UP("Up"),
    KEY_RIGHT("Right"),
    KEY_DOWN("Down"),
    KEY_COMMA(","),
    KEY_MINUS("-"),
    KEY_PERIOD("."),
    KEY_SLASH("/"),
    KEY_0("0"),
    KEY_1("1"),
    KEY_2("2"),
    KEY_3("3"),
    KEY_4("4"),
    KEY_5("5"),
    KEY_6("6"),
    KEY_7("7"),
    KEY_8("8"),
    KEY_9("9"),
    KEY_SEMICOLON(";"),
    KEY_EQUALS("="),
    KEY_A("A"),
    KEY_B("B"),
    KEY_C("C"),
    KEY_D("D"),
    KEY_E("E"),
    KEY_F("F"),
    KEY_G("G"),
    KEY_H("H"),
    KEY_I("I"),
    KEY_J("J"),
    KEY_K("K"),
    KEY_L("L"),
    KEY_M("M"),
    KEY_N("N"),
    KEY_O("O"),
    KEY_P("P"),
    KEY_Q("Q"),
    KEY_R("R"),
    KEY_S("S"),
    KEY_T("T"),
    KEY_U("U"),
    KEY_V("V"),
    KEY_W("W"),
    KEY_X("X"),
    KEY_Y("Y"),
    KEY_Z("Z"),
    KEY_OPEN_BRACKET("["),
    KEY_BACK_SLASH("\\"),
    KEY_CLOSE_BRACKET("]"),
    KEY_NUMPAD0("Numpad 0"),
    KEY_NUMPAD1("Numpad 1"),
    KEY_NUMPAD2("Numpad 2"),
    KEY_NUMPAD3("Numpad 3"),
    KEY_NUMPAD4("Numpad 4"),
    KEY_NUMPAD5("Numpad 5"),
    KEY_NUMPAD6("Numpad 6"),
    KEY_NUMPAD7("Numpad 7"),
    KEY_NUMPAD8("Numpad 8"),
    KEY_NUMPAD9("Numpad 9"),
    KEY_MULTIPLY("Numpad *"),
    KEY_ADD("Numpad +"),
    KEY_SEPARATER("Numpad ,"),
    KEY_SUBTRACT("Numpad -"),
    KEY_DECIMAL("Numpad ."),
    KEY_DIVIDE("Numpad /"),
    KEY_DELETE("Delete"),
    KEY_NUM_LOCK("Num Lock"),
    KEY_SCROLL_LOCK("Scroll Lock"),
    KEY_F1("F1"),
    KEY_F2("F2"),
    KEY_F3("F3"),
    KEY_F4("F4"),
    KEY_F5("F5"),
    KEY_F6("F6"),
    KEY_F7("F7"),
    KEY_F8("F8"),
    KEY_F9("F9"),
    KEY_F10("F10"),
    KEY_F11("F11"),
    KEY_F12("F12"),
    KEY_F13("F13"),
    KEY_F14("F14"),
    KEY_F15("F15"),
    KEY_F16("F16"),
    KEY_F17("F17"),
    KEY_F18("F18"),
    KEY_F19("F19"),
    KEY_F20("F20"),
    KEY_F21("F21"),
    KEY_F22("F22"),
    KEY_F23("F23"),
    KEY_F24("F24"),
    KEY_PRINTSCREEN("Print Screen"),
    KEY_INSERT("Insert"),
    KEY_HELP("Help"),
    KEY_META("Meta"),
    KEY_BACK_QUOTE("`"),
    KEY_QUOTE("'"),
    KEY_KP_UP("Numpad Up"),
    KEY_KP_DOWN("Numpad Down"),
    KEY_KP_LEFT("Numpad Left"),
    KEY_KP_RIGHT("Numpad Right"),
    KEY_DEAD_GRAVE("Dead Grave"),
    KEY_DEAD_ACUTE("Dead Acute"),
    KEY_DEAD_CIRCUMFLEX("Dead Circumflex"),
    KEY_DEAD_TILDE("Dead Tilde"),
    KEY_DEAD_MACRON("Dead Macron"),
    KEY_DEAD_BREVE("Dead Breve"),
    KEY_DEAD_ABOVEDOT("Dead Above Dot"),
    KEY_DEAD_DIAERESIS("Dead Diaeresis"),
    KEY_DEAD_ABOVERING("Dead Above Ring"),
    KEY_DEAD_DOUBLEACUTE("Dead Double Acute"),
    KEY_DEAD_CARON("Dead Caron"),
    KEY_DEAD_CEDILLA("Dead Cedilla"),
    KEY_DEAD_OGONEK("Dead Ogonek"),
    KEY_DEAD_IOTA("Dead Iota"),
    KEY_DEAD_VOICED_SOUND("Dead Voiced Sound"),
    KEY_DEAD_SEMIVOICED_SOUND("Dead Semivoiced Sound"),
    KEY_AMPERSAND("&"),
    KEY_ASTERISK("*"),
    KEY_QUOTEDBL("\""),
    KEY_LESS("<"),
    KEY_GREATER(">"),
    KEY_BRACELEFT("{"),
    KEY_BRACERIGHT("}"),
    KEY_AT("@"),
    KEY_COLON(":"),
    KEY_CIRCUMFLEX("^"),
    KEY_DOLLAR("$"),
    KEY_EURO_SIGN("€"),
    KEY_EXCLAMATION_MARK("!"),
    KEY_INVERTED_EXCLAMATION_MARK("¡"),
    KEY_LEFT_PARENTHESIS("("),
    KEY_NUMBER_SIGN("#"),
    KEY_PLUS("+"),
    KEY_RIGHT_PARENTHESIS(")"),
    KEY_UNDERSCORE("_"),
    KEY_WINDOWS("Windows"),
    KEY_CONTEXT_MENU("Context Menu"),
    KEY_FINAL("Final"),
    KEY_CONVERT("Convert"),
    KEY_NONCONVERT("Don't Convert"),
    KEY_ACCEPT("Accept"),
    KEY_MODECHANGE("Mode Change"),
    KEY_KANA("Kana"),
    KEY_KANJI("Kanji"),
    KEY_ALPHANUMERIC("Alphanumeric"),
    KEY_KATAKANA("Katakana"),
    KEY_HIRAGANA("Hiragana"),
    KEY_FULL_WIDTH("Full Width"),
    KEY_HALF_WIDTH("Half Width"),
    KEY_ROMAN_CHARACTERS("Roman Characters"),
    KEY_ALL_CANDIDATES("All Candidates"),
    KEY_PREVIOUS_CANDIDATE("Previous Candidate"),
    KEY_CODE_INPUT("Code Input"),
    KEY_JAPANESE_KATAKANA("Japanese Katakana"),
    KEY_JAPANESE_HIRAGANA("Japanese Hiragana"),
    KEY_JAPANESE_ROMAN("Japanese Roman"),
    KEY_KANA_LOCK("Kana Lock"),
    KEY_INPUT_METHOD_ON_OFF("Input Method On/Off"),
    KEY_CUT("Cut"),
    KEY_COPY("Copy"),
    KEY_PASTE("Paste"),
    KEY_UNDO("Undo"),
    KEY_AGAIN("Again"),
    KEY_FIND("Find"),
    KEY_PROPS("Props"),
    KEY_STOP("Stop"),
    KEY_COMPOSE("Compose"),
    KEY_ALT_GRAPH("Alt Graph"),
    KEY_BEGIN("Begin");

    /* ================ [ FIELDS ] ================ */

    /** The name of the input */
    private String name;

    /**
     * Creates a new Input with the given name
     * 
     * @param name The name of the input
     */
    @JsonCreator
    Input(String name) { this.name = name; }

    /* ================ [ METHODS ] ================ */

    /**
     * Get the name of the input
     * 
     * @return The name of the input
     */
    @JsonValue
    public String getName() { return name; }

    /**
     * Get the input by name
     * 
     * @param name The name of the input
     * @return The input
     */
    public static Input getByName(String name) {
        for (Input input : values()) {
            if (input.name.equals(name)) {
                return input;
            }
        } return null;
    }

    /* ================ [ INPUTS ] ================ */

    /**
     * Get the input from a mouse button
     *
     * @param button The mouse button
     * @return The input
     */
    public static Input getFromMouse(int button) {
        switch (button) {
            case MouseEvent.BUTTON1: return MOUSE_LEFT;
            case MouseEvent.BUTTON2: return MOUSE_RIGHT;
            case MouseEvent.BUTTON3: return MOUSE_MIDDLE;
            default: return null;
        }
    }

    /**
     * Get the input from a keyboard key
     *
     * @param key The key
     * @return The input
     */
    public static Input getFromKey(int key) {
        switch (key) {
            case KeyEvent.VK_ENTER: return KEY_ENTER;
            case KeyEvent.VK_BACK_SPACE: return KEY_BACK_SPACE;
            case KeyEvent.VK_TAB: return KEY_TAB;
            case KeyEvent.VK_CANCEL: return KEY_CANCEL;
            case KeyEvent.VK_CLEAR: return KEY_CLEAR;
            case KeyEvent.VK_SHIFT: return KEY_SHIFT;
            case KeyEvent.VK_CONTROL: return KEY_CONTROL;
            case KeyEvent.VK_ALT: return KEY_ALT;
            case KeyEvent.VK_PAUSE: return KEY_PAUSE;
            case KeyEvent.VK_CAPS_LOCK: return KEY_CAPS_LOCK;
            case KeyEvent.VK_ESCAPE: return KEY_ESCAPE;
            case KeyEvent.VK_SPACE: return KEY_SPACE;
            case KeyEvent.VK_PAGE_UP: return KEY_PAGE_UP;
            case KeyEvent.VK_PAGE_DOWN: return KEY_PAGE_DOWN;
            case KeyEvent.VK_END: return KEY_END;
            case KeyEvent.VK_HOME: return KEY_HOME;
            case KeyEvent.VK_LEFT: return KEY_LEFT;
            case KeyEvent.VK_UP: return KEY_UP;
            case KeyEvent.VK_RIGHT: return KEY_RIGHT;
            case KeyEvent.VK_DOWN: return KEY_DOWN;
            case KeyEvent.VK_COMMA: return KEY_COMMA;
            case KeyEvent.VK_MINUS: return KEY_MINUS;
            case KeyEvent.VK_PERIOD: return KEY_PERIOD;
            case KeyEvent.VK_SLASH: return KEY_SLASH;
            case KeyEvent.VK_0: return KEY_0;
            case KeyEvent.VK_1: return KEY_1;
            case KeyEvent.VK_2: return KEY_2;
            case KeyEvent.VK_3: return KEY_3;
            case KeyEvent.VK_4: return KEY_4;
            case KeyEvent.VK_5: return KEY_5;
            case KeyEvent.VK_6: return KEY_6;
            case KeyEvent.VK_7: return KEY_7;
            case KeyEvent.VK_8: return KEY_8;
            case KeyEvent.VK_9: return KEY_9;
            case KeyEvent.VK_SEMICOLON: return KEY_SEMICOLON;
            case KeyEvent.VK_EQUALS: return KEY_EQUALS;
            case KeyEvent.VK_A: return KEY_A;
            case KeyEvent.VK_B: return KEY_B;
            case KeyEvent.VK_C: return KEY_C;
            case KeyEvent.VK_D: return KEY_D;
            case KeyEvent.VK_E: return KEY_E;
            case KeyEvent.VK_F: return KEY_F;
            case KeyEvent.VK_G: return KEY_G;
            case KeyEvent.VK_H: return KEY_H;
            case KeyEvent.VK_I: return KEY_I;
            case KeyEvent.VK_J: return KEY_J;
            case KeyEvent.VK_K: return KEY_K;
            case KeyEvent.VK_L: return KEY_L;
            case KeyEvent.VK_M: return KEY_M;
            case KeyEvent.VK_N: return KEY_N;
            case KeyEvent.VK_O: return KEY_O;
            case KeyEvent.VK_P: return KEY_P;
            case KeyEvent.VK_Q: return KEY_Q;
            case KeyEvent.VK_R: return KEY_R;
            case KeyEvent.VK_S: return KEY_S;
            case KeyEvent.VK_T: return KEY_T;
            case KeyEvent.VK_U: return KEY_U;
            case KeyEvent.VK_V: return KEY_V;
            case KeyEvent.VK_W: return KEY_W;
            case KeyEvent.VK_X: return KEY_X;
            case KeyEvent.VK_Y: return KEY_Y;
            case KeyEvent.VK_Z: return KEY_Z;
            case KeyEvent.VK_OPEN_BRACKET: return KEY_OPEN_BRACKET;
            case KeyEvent.VK_BACK_SLASH: return KEY_BACK_SLASH;
            case KeyEvent.VK_CLOSE_BRACKET: return KEY_CLOSE_BRACKET;
            case KeyEvent.VK_NUMPAD0: return KEY_NUMPAD0;
            case KeyEvent.VK_NUMPAD1: return KEY_NUMPAD1;
            case KeyEvent.VK_NUMPAD2: return KEY_NUMPAD2;
            case KeyEvent.VK_NUMPAD3: return KEY_NUMPAD3;
            case KeyEvent.VK_NUMPAD4: return KEY_NUMPAD4;
            case KeyEvent.VK_NUMPAD5: return KEY_NUMPAD5;
            case KeyEvent.VK_NUMPAD6: return KEY_NUMPAD6;
            case KeyEvent.VK_NUMPAD7: return KEY_NUMPAD7;
            case KeyEvent.VK_NUMPAD8: return KEY_NUMPAD8;
            case KeyEvent.VK_NUMPAD9: return KEY_NUMPAD9;
            case KeyEvent.VK_MULTIPLY: return KEY_MULTIPLY;
            case KeyEvent.VK_ADD: return KEY_ADD;
            case KeyEvent.VK_SEPARATER: return KEY_SEPARATER;
            case KeyEvent.VK_SUBTRACT: return KEY_SUBTRACT;
            case KeyEvent.VK_DECIMAL: return KEY_DECIMAL;
            case KeyEvent.VK_DIVIDE: return KEY_DIVIDE;
            case KeyEvent.VK_DELETE: return KEY_DELETE;
            case KeyEvent.VK_NUM_LOCK: return KEY_NUM_LOCK;
            case KeyEvent.VK_SCROLL_LOCK: return KEY_SCROLL_LOCK;
            case KeyEvent.VK_F1: return KEY_F1;
            case KeyEvent.VK_F2: return KEY_F2;
            case KeyEvent.VK_F3: return KEY_F3;
            case KeyEvent.VK_F4: return KEY_F4;
            case KeyEvent.VK_F5: return KEY_F5;
            case KeyEvent.VK_F6: return KEY_F6;
            case KeyEvent.VK_F7: return KEY_F7;
            case KeyEvent.VK_F8: return KEY_F8;
            case KeyEvent.VK_F9: return KEY_F9;
            case KeyEvent.VK_F10: return KEY_F10;
            case KeyEvent.VK_F11: return KEY_F11;
            case KeyEvent.VK_F12: return KEY_F12;
            case KeyEvent.VK_F13: return KEY_F13;
            case KeyEvent.VK_F14: return KEY_F14;
            case KeyEvent.VK_F15: return KEY_F15;
            case KeyEvent.VK_F16: return KEY_F16;
            case KeyEvent.VK_F17: return KEY_F17;
            case KeyEvent.VK_F18: return KEY_F18;
            case KeyEvent.VK_F19: return KEY_F19;
            case KeyEvent.VK_F20: return KEY_F20;
            case KeyEvent.VK_F21: return KEY_F21;
            case KeyEvent.VK_F22: return KEY_F22;
            case KeyEvent.VK_F23: return KEY_F23;
            case KeyEvent.VK_F24: return KEY_F24;
            case KeyEvent.VK_PRINTSCREEN: return KEY_PRINTSCREEN;
            case KeyEvent.VK_INSERT: return KEY_INSERT;
            case KeyEvent.VK_HELP: return KEY_HELP;
            case KeyEvent.VK_META: return KEY_META;
            case KeyEvent.VK_BACK_QUOTE: return KEY_BACK_QUOTE;
            case KeyEvent.VK_QUOTE: return KEY_QUOTE;
            case KeyEvent.VK_KP_UP: return KEY_KP_UP;
            case KeyEvent.VK_KP_DOWN: return KEY_KP_DOWN;
            case KeyEvent.VK_KP_LEFT: return KEY_KP_LEFT;
            case KeyEvent.VK_KP_RIGHT: return KEY_KP_RIGHT;
            case KeyEvent.VK_DEAD_GRAVE: return KEY_DEAD_GRAVE;
            case KeyEvent.VK_DEAD_ACUTE: return KEY_DEAD_ACUTE;
            case KeyEvent.VK_DEAD_CIRCUMFLEX: return KEY_DEAD_CIRCUMFLEX;
            case KeyEvent.VK_DEAD_TILDE: return KEY_DEAD_TILDE;
            case KeyEvent.VK_DEAD_MACRON: return KEY_DEAD_MACRON;
            case KeyEvent.VK_DEAD_BREVE: return KEY_DEAD_BREVE;
            case KeyEvent.VK_DEAD_ABOVEDOT: return KEY_DEAD_ABOVEDOT;
            case KeyEvent.VK_DEAD_DIAERESIS: return KEY_DEAD_DIAERESIS;
            case KeyEvent.VK_DEAD_ABOVERING: return KEY_DEAD_ABOVERING;
            case KeyEvent.VK_DEAD_DOUBLEACUTE: return KEY_DEAD_DOUBLEACUTE;
            case KeyEvent.VK_DEAD_CARON: return KEY_DEAD_CARON;
            case KeyEvent.VK_DEAD_CEDILLA: return KEY_DEAD_CEDILLA;
            case KeyEvent.VK_DEAD_OGONEK: return KEY_DEAD_OGONEK;
            case KeyEvent.VK_DEAD_IOTA: return KEY_DEAD_IOTA;
            case KeyEvent.VK_DEAD_VOICED_SOUND: return KEY_DEAD_VOICED_SOUND;
            case KeyEvent.VK_DEAD_SEMIVOICED_SOUND: return KEY_DEAD_SEMIVOICED_SOUND;
            case KeyEvent.VK_AMPERSAND: return KEY_AMPERSAND;
            case KeyEvent.VK_ASTERISK: return KEY_ASTERISK;
            case KeyEvent.VK_QUOTEDBL: return KEY_QUOTEDBL;
            case KeyEvent.VK_LESS: return KEY_LESS;
            case KeyEvent.VK_GREATER: return KEY_GREATER;
            case KeyEvent.VK_BRACELEFT: return KEY_BRACELEFT;
            case KeyEvent.VK_BRACERIGHT: return KEY_BRACERIGHT;
            case KeyEvent.VK_AT: return KEY_AT;
            case KeyEvent.VK_COLON: return KEY_COLON;
            case KeyEvent.VK_CIRCUMFLEX: return KEY_CIRCUMFLEX;
            case KeyEvent.VK_DOLLAR: return KEY_DOLLAR;
            case KeyEvent.VK_EURO_SIGN: return KEY_EURO_SIGN;
            case KeyEvent.VK_EXCLAMATION_MARK: return KEY_EXCLAMATION_MARK;
            case KeyEvent.VK_INVERTED_EXCLAMATION_MARK: return KEY_INVERTED_EXCLAMATION_MARK;
            case KeyEvent.VK_LEFT_PARENTHESIS: return KEY_LEFT_PARENTHESIS;
            case KeyEvent.VK_NUMBER_SIGN: return KEY_NUMBER_SIGN;
            case KeyEvent.VK_PLUS: return KEY_PLUS;
            case KeyEvent.VK_RIGHT_PARENTHESIS: return KEY_RIGHT_PARENTHESIS;
            case KeyEvent.VK_UNDERSCORE: return KEY_UNDERSCORE;
            case KeyEvent.VK_WINDOWS: return KEY_WINDOWS;
            case KeyEvent.VK_CONTEXT_MENU: return KEY_CONTEXT_MENU;
            case KeyEvent.VK_FINAL: return KEY_FINAL;
            case KeyEvent.VK_CONVERT: return KEY_CONVERT;
            case KeyEvent.VK_NONCONVERT: return KEY_NONCONVERT;
            case KeyEvent.VK_ACCEPT: return KEY_ACCEPT;
            case KeyEvent.VK_MODECHANGE: return KEY_MODECHANGE;
            case KeyEvent.VK_KANA: return KEY_KANA;
            case KeyEvent.VK_KANJI: return KEY_KANJI;
            case KeyEvent.VK_ALPHANUMERIC: return KEY_ALPHANUMERIC;
            case KeyEvent.VK_KATAKANA: return KEY_KATAKANA;
            case KeyEvent.VK_HIRAGANA: return KEY_HIRAGANA;
            case KeyEvent.VK_FULL_WIDTH: return KEY_FULL_WIDTH;
            case KeyEvent.VK_HALF_WIDTH: return KEY_HALF_WIDTH;
            case KeyEvent.VK_ROMAN_CHARACTERS: return KEY_ROMAN_CHARACTERS;
            case KeyEvent.VK_ALL_CANDIDATES: return KEY_ALL_CANDIDATES;
            case KeyEvent.VK_PREVIOUS_CANDIDATE: return KEY_PREVIOUS_CANDIDATE;
            case KeyEvent.VK_CODE_INPUT: return KEY_CODE_INPUT;
            case KeyEvent.VK_JAPANESE_KATAKANA: return KEY_JAPANESE_KATAKANA;
            case KeyEvent.VK_JAPANESE_HIRAGANA: return KEY_JAPANESE_HIRAGANA;
            case KeyEvent.VK_JAPANESE_ROMAN: return KEY_JAPANESE_ROMAN;
            case KeyEvent.VK_KANA_LOCK: return KEY_KANA_LOCK;
            case KeyEvent.VK_INPUT_METHOD_ON_OFF: return KEY_INPUT_METHOD_ON_OFF;
            case KeyEvent.VK_CUT: return KEY_CUT;
            case KeyEvent.VK_COPY: return KEY_COPY;
            case KeyEvent.VK_PASTE: return KEY_PASTE;
            case KeyEvent.VK_UNDO: return KEY_UNDO;
            case KeyEvent.VK_AGAIN: return KEY_AGAIN;
            case KeyEvent.VK_FIND: return KEY_FIND;
            case KeyEvent.VK_PROPS: return KEY_PROPS;
            case KeyEvent.VK_STOP: return KEY_STOP;
            case KeyEvent.VK_COMPOSE: return KEY_COMPOSE;
            case KeyEvent.VK_ALT_GRAPH: return KEY_ALT_GRAPH;
            case KeyEvent.VK_BEGIN: return KEY_BEGIN;
            default: return null;
        }
    }

}
