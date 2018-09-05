package moerspace.learningtest.keddit.commons

import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable

abstract class RxBaseFragment : Fragment() {
    protected var disposables = CompositeDisposable()

    override fun onResume() {
        super.onResume()
        disposables = CompositeDisposable()
    }

    override fun onPause() {
        super.onPause()
        if (disposables.isDisposed.not()) {
            disposables.dispose()
        }
        disposables.clear()
    }
}