package moerspace.learningtest.keddit.commons

import androidx.fragment.app.Fragment
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