package de.mymiggi.voc.trainer;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.google.gson.Gson;

import de.mymiggi.voc.trainer.actions.AddSpecialWordAction;
import de.mymiggi.voc.trainer.actions.BindDictionary;
import de.mymiggi.voc.trainer.actions.DeleteDictionaryAction;
import de.mymiggi.voc.trainer.actions.GetBondedDictionary;
import de.mymiggi.voc.trainer.actions.GetDictionaryByIDAction;
import de.mymiggi.voc.trainer.actions.GetDictionaryByUserAction;
import de.mymiggi.voc.trainer.actions.GetPreviewAction;
import de.mymiggi.voc.trainer.actions.GetSpecialWordIDsAction;
import de.mymiggi.voc.trainer.actions.RemoveSpecialWordAction;
import de.mymiggi.voc.trainer.actions.SaveDictionaryAction;
import de.mymiggi.voc.trainer.actions.SearchDictionaryAction;
import de.mymiggi.voc.trainer.actions.UpdateDictionaryAction;
import de.mymiggi.voc.trainer.actions.helper.BuildUserFromContext;
import de.mymiggi.voc.trainer.actions.helper.CleanDataBaseAction;
import de.mymiggi.voc.trainer.entity.Dictionary;
import de.mymiggi.voc.trainer.entity.DiscordUser;
import de.mymiggi.voc.trainer.entity.SearchRequest;
import de.mymiggi.voc.trainer.entity.db.SpecialWord;
import de.mymiggi.voc.trainer.entity.db.Words;
import de.mymiggi.voc.trainer.manager.BondedDictionaryManager;
import de.mymiggi.voc.trainer.manager.DictionaryEntryManager;
import de.mymiggi.voc.trainer.manager.SpecialWordManager;
import de.mymiggi.voc.trainer.manager.WordsManager;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DictionaryResource
{
	/* LOL */
	public static final UniversalHibernateClient HIBERNATE_CLIENT = new UniversalHibernateClient();
	public static final WordsManager WORDS_MANAGER = new WordsManager();
	public static final DictionaryEntryManager DICTIONARY_MANAGER = new DictionaryEntryManager();
	public static final BondedDictionaryManager BONDED_DICTIONARY_MANAGER = new BondedDictionaryManager();
	public static final SpecialWordManager SPECIAL_WORD_MANAGER = new SpecialWordManager();

	@POST
	@Path("search")
	@PermitAll
	public Response search(SearchRequest searchRequest)
	{
		return new SearchDictionaryAction().run(searchRequest);
	}

	@GET
	@Path("get/preview")
	@PermitAll
	public Response getpreview()
	{
		return new GetPreviewAction().run();
	}

	@GET
	@Path("get/dictionary")
	public Response getDictionary(@QueryParam("id") String id)
	{
		return new GetDictionaryByIDAction().run(id);
	}

	@GET
	@Path("get/my-dictionarys")
	@RolesAllowed({ "user", "admin" })
	public Response getMyDictionary(@Context SecurityContext ctx)
	{
		return new GetDictionaryByUserAction().run(getUser(ctx));
	}

	@GET
	@Path("get/bonded-dictionary")
	@RolesAllowed({ "user", "admin" })
	public Response getBondedDictionary(@Context SecurityContext ctx)
	{
		return new GetBondedDictionary().run(getUser(ctx));
	}

	@GET
	@Path("get/special-word-ids")
	@RolesAllowed({ "user", "admin" })
	public Response getSpecialWordIs(@Context SecurityContext ctx)
	{
		return new GetSpecialWordIDsAction().run(getUser(ctx));
	}

	@PUT
	@Path("update/dictionary")
	@RolesAllowed({ "user", "admin" })
	public Response updateDictionary(@Context SecurityContext ctx, @QueryParam("id") String id, List<Words> newWords)
	{
		return new UpdateDictionaryAction().run(id, getUser(ctx), newWords);
	}

	@PUT
	@Path("save")
	@RolesAllowed({ "user", "admin" })
	public Response save(@Context SecurityContext ctx, Dictionary dictionary)
	{
		return new SaveDictionaryAction().run(dictionary, getUser(ctx));
	}

	@PUT
	@Path("add/special-word")
	@RolesAllowed({ "user", "admin" })
	public Response addSpecialWord(@Context SecurityContext ctx, SpecialWord specialWord)
	{
		return new AddSpecialWordAction().run(getUser(ctx), specialWord);
	}

	@PUT
	@Path("remove/special-word")
	@RolesAllowed({ "user", "admin" })
	public Response removeSpecialWord(@Context SecurityContext ctx, SpecialWord specialWord)
	{
		return new RemoveSpecialWordAction().run(getUser(ctx), specialWord);
	}

	@PUT
	@Path("bind")
	@RolesAllowed({ "user", "admin" })
	public Response bind(@Context SecurityContext ctx, @QueryParam("id") String id)
	{
		return new BindDictionary().run(id, getUser(ctx));
	}

	@PUT
	@Path("sync-db")
	@RolesAllowed({ "admin" })
	public Response sync()
	{
		WORDS_MANAGER.syncList();
		DICTIONARY_MANAGER.syncList();
		BONDED_DICTIONARY_MANAGER.syncList();
		SPECIAL_WORD_MANAGER.syncList();
		new CleanDataBaseAction().run();
		return Response.ok().build();
	}

	@DELETE
	@Path("delete")
	@RolesAllowed({ "user", "admin" })
	public Response delete(Dictionary dictionary)
	{
		return new DeleteDictionaryAction().run(dictionary);
	}

	@GET
	@Path("admin")
	@RolesAllowed({ "admin" })
	public Response admin(@Context SecurityContext ctx)
	{
		return Response.ok().build();
	}

	@GET
	@Path("test")
	@RolesAllowed({ "user", "admin" })
	@Produces(MediaType.TEXT_PLAIN)
	public Response test(@Context SecurityContext ctx)
	{
		return Response.ok(new Gson().toJson(ctx.getUserPrincipal())).build();
	}

	private DiscordUser getUser(SecurityContext ctx)
	{
		return new BuildUserFromContext().run(ctx);
	}
}
