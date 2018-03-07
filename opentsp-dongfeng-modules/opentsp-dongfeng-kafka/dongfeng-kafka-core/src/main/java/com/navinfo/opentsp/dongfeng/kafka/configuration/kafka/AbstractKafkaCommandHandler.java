package com.navinfo.opentsp.dongfeng.kafka.configuration.kafka;


/**
 * Abstract command handler
 * platform will dispatch commands to the handler by commandType (equals to Service Activator in integration patterns)
 * Use subclasses for implementing logic of processing commands
 *
 */
public abstract class AbstractKafkaCommandHandler<C extends KafkaCommand> implements KafkaCommandHandler<C> {
    private final Class<C> commandType;

    protected AbstractKafkaCommandHandler(final Class<C> commandType) {
        this.commandType = commandType;
    }

    /**
     * @return command type for routing it to appropriate handler
     */
    @Override
    public Class<C> getCommandType() {
        return commandType;
    }

}
