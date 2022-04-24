package com.njp.example.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.njp.example.ui.issue.IssueFragment
import com.njp.example.ui.repo.RepoFragment

class FragmentFactoryImpl() : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String) : Fragment {
        return when(className) {
            IssueFragment::class.java.name -> IssueFragment(Pair("JakeWharton", "hugo"))
            RepoFragment::class.java.name -> RepoFragment("JakeWharton")
            else -> super.instantiate(classLoader, className)
        }
    }
}