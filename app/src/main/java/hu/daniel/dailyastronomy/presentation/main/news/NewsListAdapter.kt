package hu.daniel.dailyastronomy.presentation.main.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import hu.daniel.dailyastronomy.R
import hu.daniel.dailyastronomy.databinding.ComponentNewsListItemBinding
import hu.daniel.dailyastronomy.dto.Article


class NewsListAdapter(private val viewModel: NewsViewModel) : RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    val news: MutableList<Article> = arrayListOf()

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        news[position].let { article ->
            holder.binding.article = article
            holder.binding.vm = viewModel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.component_news_list_item, parent, false))
    }

    class NewsViewHolder(val binding: ComponentNewsListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = news.size
}