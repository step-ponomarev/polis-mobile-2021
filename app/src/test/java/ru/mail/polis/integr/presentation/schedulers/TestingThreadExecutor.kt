package ru.mail.polis.integr.presentation.schedulers

import ru.mail.polis.domain.executor.ThreadExecutor
import io.reactivex.schedulers.Schedulers

class TestingThreadExecutor : ThreadExecutor {
    override fun execute(command: Runnable?) {
        Schedulers.trampoline().scheduleDirect(command!!)
    }
}
