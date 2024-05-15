package com.adishakti.repohunt.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.adishakti.repohunt.R
import com.adishakti.repohunt.components.AppBar
import com.adishakti.repohunt.components.ErrorScreen
import com.adishakti.repohunt.data.model.Contributor
import com.adishakti.repohunt.data.model.RepoItem
import com.adishakti.repohunt.ui.viewModels.RepoDetailsViewModel
import com.adishakti.repohunt.util.ResourceState
import com.adishakti.repohunt.util.toStringOrEmpty


@Composable
fun RepoDetailsScreen(
    onBackClick: () -> Unit,
    onUrlClick: (String) -> Unit,
    nameArgument: RepoItem?,
    viewModel: RepoDetailsViewModel = hiltViewModel(),
) {
    val repoDetailsState by viewModel.repositoryDetails.collectAsState()
    nameArgument?.let {
        viewModel.getRepositoryDetails(it.name.toStringOrEmpty(), it.owner?.login.toStringOrEmpty())
    }

    val contributors = arrayListOf<Contributor>()
    val ctx = LocalContext.current
    when (val result = repoDetailsState) {
        is ResourceState.Success -> {
            contributors.clear()
            contributors.addAll(result.data)
            Column {
                AppBar("Repo Details", modifier = Modifier, onBackClick = onBackClick)
                Contributors(contributors, nameArgument, onUrlClick)
            }

        }

        is ResourceState.Error -> {
            ErrorScreen(
                isInternetConnected = viewModel.isInternetConnected(),
                contributors.isEmpty(),
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                if (viewModel.isInternetConnected()) {
                    nameArgument?.let {
                        viewModel.getRepositoryDetails(
                            it.name.toStringOrEmpty(),
                            it.owner?.login.toStringOrEmpty()
                        )
                    }
                } else {
                    Toast.makeText(
                        ctx,
                        ctx.getString(R.string.connect_internet),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        is ResourceState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        else -> {

        }
    }
}


@Preview
@Composable
fun show() {
    Contributors(
        contributors = listOf(
            Contributor("Hello", "https://avatars.githubusercontent.com/u/23095877?v=4"),
            Contributor("Temp", "https://avatars.githubusercontent.com/u/23095877?v=4")
        ),
        repo = RepoItem("Prince Sharma", "Prince", ""), {})
}

@Composable
fun Contributors(
    contributors: List<Contributor>,
    repo: RepoItem?,
    onUrlClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "Repo Details:",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(repo?.owner?.avatar_url),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Name: ${repo?.name}",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Button(
                        onClick = { repo?.svn_url?.let(onUrlClick) },
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        Text(text = "View Project")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Description: ${repo?.description ?: "No description"}",
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Contributors:",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        itemsIndexed(chunkedContributors(contributors)) { index, contributorRow ->
            if (index > 0) {
                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Color.LightGray, thickness = 1.dp)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                contributorRow.forEach { contributor ->
                    ContributorItem(contributor = contributor, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun ContributorItem(
    contributor: Contributor,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .size(120.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(contributor.avatar_url),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(64.dp)
                    .padding(vertical = 8.dp)
                    .clip(CircleShape)
            )
            Text(
                text = contributor.login ?: "",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

private const val CONTRIBUTORS_PER_ROW = 4

private fun chunkedContributors(contributors: List<Contributor>): List<List<Contributor>> {
    return contributors.chunked(CONTRIBUTORS_PER_ROW)
}
