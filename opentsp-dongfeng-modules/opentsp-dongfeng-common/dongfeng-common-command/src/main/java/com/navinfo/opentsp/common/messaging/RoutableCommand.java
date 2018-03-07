package com.navinfo.opentsp.common.messaging;

import com.navinfo.opentsp.common.messaging.routing.MessageGroupInfo;

public abstract interface RoutableCommand {
    public abstract MessageGroupInfo getRoute();
}