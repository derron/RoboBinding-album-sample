package org.robobinding.albumsample.activity;

import org.robobinding.albumsample.R;
import org.robobinding.albumsample.model.Album;
import org.robobinding.albumsample.presentationmodel.CreateEditAlbumView;
import org.robobinding.albumsample.store.AlbumStore;
import org.robobinding.annotation.DependsOnStateOf;
import org.robobinding.annotation.PresentationModel;

import android.os.Bundle;

/**
 * @author Cheng Wei
 * @author Robert Taylor
 * @since 1.0
 */
public class CreateEditAlbumActivity extends AbstractActivity implements CreateEditAlbumView {

    public static final String ALBUM_ID = ViewAlbumActivity.ALBUM_ID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long albumId = getIntent().getLongExtra(ALBUM_ID, Album.NO_ID);

        Album.Builder albumBuilder;
        if (Album.isNew(albumId))
            albumBuilder = new Album.Builder();
        else {
            Album album = getAlbumStore().get(albumId);
            albumBuilder = album.createBuilder();
        }


        CreateEditAlbumPresentationModel presentationModel = new CreateEditAlbumPresentationModel(this, getAlbumStore(), albumBuilder);
        initializeContentView(R.layout.activity_create_edit_album, presentationModel);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public String getCreateAlbumTitle() {
        return getString(R.string.create_album);
    }

    /**
     * @author Cheng Wei
     * @author Robert Taylor
     * @since 1.0
     */
    @PresentationModel
    public static class CreateEditAlbumPresentationModel {
        private static final String CLASSICAL = "classical";

        private final CreateEditAlbumView view;
        private final AlbumStore albumStore;
        private final Album.Builder albumBuilder;

        public CreateEditAlbumPresentationModel(CreateEditAlbumView view, AlbumStore albumStore,
                                                Album.Builder albumBuilder) {
            this.view = view;
            this.albumStore = albumStore;
            this.albumBuilder = albumBuilder;
        }

        public void save() {
            albumStore.save(albumBuilder.create());
            view.finishActivity();
        }

        public String getTitle() {
            return albumBuilder.getTitle();
        }

        public void setTitle(String title) {
            albumBuilder.setTitle(title);
        }

        public String getArtist() {
            return albumBuilder.getArtist();
        }

        public void setArtist(String artist) {
            albumBuilder.setArtist(artist);
        }

        public boolean isClassical() {
            return albumBuilder.isClassical();
        }

        public void setClassical(boolean classical) {
            albumBuilder.setClassical(classical);
        }

        @DependsOnStateOf(CLASSICAL)
        public boolean isComposerEnabled() {
            return isClassical();
        }

        public String getComposer() {
            return albumBuilder.getComposer();
        }

        public void setComposer(String composer) {
            albumBuilder.setComposer(composer);
        }

        @DependsOnStateOf(CLASSICAL)
        public String getWindowTitle() {
            if (albumBuilder.isNew())
                return view.getCreateAlbumTitle();

            return isClassical() ? "Edit Classical Album" : "Edit Album";
        }
    }
}