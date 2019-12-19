package fr.ensim.xml.deezer.dom;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Test;
import org.xml.sax.SAXException;

import fr.ensim.xml.deezer.data.Album;
import fr.ensim.xml.deezer.data.Track;

public class SearchAlbumTracksTest {
        static {
                DOMConfigurator.configure("log4J.xml");
        }

        @Test
        public void testFindInputStream() throws SAXException, ParserConfigurationException, IOException {
            InputStream in = getClass().getResourceAsStream("../album-stupeflip.xml");

            ArrayList<Track> listTracks = new ArrayList<Track>();
                listTracks =    SearchAlbumTracks.find(in);

                for(int i = 0 ; i<listTracks.size();i++)
                {
                        System.out.println(listTracks.get(i).getTitle());
                }
        }

}