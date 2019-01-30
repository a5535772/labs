package com.leo.labs.hystrix.filters.command;

import com.netflix.hystrix.HystrixExecutable;

public interface LeoLabCommand extends HystrixExecutable<LeoLabResponse> {

}
