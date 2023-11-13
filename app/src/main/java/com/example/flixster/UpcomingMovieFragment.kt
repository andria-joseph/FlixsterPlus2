package com.example.flixster

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers

const val MOVIE_EXTRA = "MOVIE_EXTRA"
private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class UpcomingMovieFragment: Fragment(), OnListFragmentInteractionListener {
    /*
    * Constructing the view
    */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        // Create and set up an AsyncHTTPClient() here
        progressBar.show()
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY
        // Using the client, perform the HTTP request
        client["https://api.themoviedb.org/3/movie/upcoming?language=en-US&page=1", params,
            object : JsonHttpResponseHandler() {
                /*
                 * The onSuccess function gets called when
                 * HTTP response status is "200 OK"
                 */
                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JsonHttpResponseHandler.JSON
                ) {
                    // The wait for a response is over
                    progressBar.hide()
                    val resultsJSON: String = json.jsonObject.get("results").toString()
                    val gson = Gson()
                    val arrayMovieType = object : TypeToken<List<UpcomingMovieObj>>(){}.type

                    val models: List<UpcomingMovieObj> =
                        gson.fromJson(resultsJSON, arrayMovieType)
                    recyclerView.adapter =
                        UpcomingMovieRecyclerViewAdapter(models, this@UpcomingMovieFragment)

                    // Look for this in Logcat:
                    Log.d("UpcomingMovieFragment", "response successful")
                }

                /*
                 * The onFailure function gets called when
                 * HTTP response status is "4XX" (eg. 401, 403, 404)
                 */
                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    t: Throwable?
                ) {
                    // The wait for a response is over
                    progressBar.hide()

                    // If the error is not null, log it!
                    t?.message?.let {
                        Log.e("UpcomingMovieFragment", errorResponse)
                    }
                }
            }]
    }

    /*
    * What happens when a particular movie is clicked.
    */
    override fun onItemClick(item: UpcomingMovieObj) {
        val intent = Intent(context, UpcomingMovieDetailActivity::class.java)
        intent.putExtra("title", item.title.toString())
        intent.putExtra("image", item.posterImageUrl.toString())
        intent.putExtra("abstract", item.overview.toString())
        intent.putExtra("language", item.originalLanguage.toString())
        intent.putExtra("date", item.releaseDate.toString())
        startActivity(intent)
    }
}