package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import org.example.interfaces.Command;
import org.example.ui.commands.BinaryCommand;
import org.example.ui.commands.MatrixCommand;
import org.example.ui.commands.ToggleCommand;
import org.example.ui.commands.UnaryCommand;


public class CommandModule extends AbstractModule {

    @Override
    protected void configure() {

        MapBinder<String, Command> mapBinder =
                MapBinder.newMapBinder(binder(), String.class, Command.class);

        mapBinder.addBinding("unary").to(UnaryCommand.class);
        mapBinder.addBinding("binary").to(BinaryCommand.class);
        mapBinder.addBinding("matrix").to(MatrixCommand.class);
        mapBinder.addBinding("toggle").to(ToggleCommand.class);
    }
}