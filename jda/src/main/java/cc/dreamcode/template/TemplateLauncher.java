package cc.dreamcode.template;

import cc.dreamcode.platform.cli.DreamCliPlatform;

public class TemplateLauncher {
    public static void main(String[] args) {
        // Use argument or create config.
        DreamCliPlatform.run(new TemplateBot());
    }
}
