#pragma checksum "C:\Users\User\Desktop\ParkBuddies_25.1\ParkBuddies\ParkBuddies\Views\User\Create.cshtml" "{ff1816ec-aa5e-4d10-87f7-6f4963833460}" "2cf781d16a0dd34b7d54e2279d529663492234c3"
// <auto-generated/>
#pragma warning disable 1591
[assembly: global::Microsoft.AspNetCore.Razor.Hosting.RazorCompiledItemAttribute(typeof(AspNetCore.Views_User_Create), @"mvc.1.0.view", @"/Views/User/Create.cshtml")]
[assembly:global::Microsoft.AspNetCore.Mvc.Razor.Compilation.RazorViewAttribute(@"/Views/User/Create.cshtml", typeof(AspNetCore.Views_User_Create))]
namespace AspNetCore
{
    #line hidden
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Threading.Tasks;
    using Microsoft.AspNetCore.Mvc;
    using Microsoft.AspNetCore.Mvc.Rendering;
    using Microsoft.AspNetCore.Mvc.ViewFeatures;
#line 1 "C:\Users\User\Desktop\ParkBuddies_25.1\ParkBuddies\ParkBuddies\Views\_ViewImports.cshtml"
using ParkBuddies;

#line default
#line hidden
#line 2 "C:\Users\User\Desktop\ParkBuddies_25.1\ParkBuddies\ParkBuddies\Views\_ViewImports.cshtml"
using ParkBuddies.Models;

#line default
#line hidden
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"2cf781d16a0dd34b7d54e2279d529663492234c3", @"/Views/User/Create.cshtml")]
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"a3ce52700fe2db686e17e796f3374ef95f34b00b", @"/Views/_ViewImports.cshtml")]
    public class Views_User_Create : global::Microsoft.AspNetCore.Mvc.Razor.RazorPage<ParkBuddies.Models.User>
    {
        private static readonly global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute __tagHelperAttribute_0 = new global::Microsoft.AspNetCore.Razor.TagHelpers.TagHelperAttribute("src", new global::Microsoft.AspNetCore.Html.HtmlString("~/lib/jquery/dist/jquery.min.js"), global::Microsoft.AspNetCore.Razor.TagHelpers.HtmlAttributeValueStyle.DoubleQuotes);
        #line hidden
        #pragma warning disable 0169
        private string __tagHelperStringValueBuffer;
        #pragma warning restore 0169
        private global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperExecutionContext __tagHelperExecutionContext;
        private global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperRunner __tagHelperRunner = new global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperRunner();
        private global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperScopeManager __backed__tagHelperScopeManager = null;
        private global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperScopeManager __tagHelperScopeManager
        {
            get
            {
                if (__backed__tagHelperScopeManager == null)
                {
                    __backed__tagHelperScopeManager = new global::Microsoft.AspNetCore.Razor.Runtime.TagHelpers.TagHelperScopeManager(StartTagHelperWritingScope, EndTagHelperWritingScope);
                }
                return __backed__tagHelperScopeManager;
            }
        }
        private global::Microsoft.AspNetCore.Mvc.Razor.TagHelpers.UrlResolutionTagHelper __Microsoft_AspNetCore_Mvc_Razor_TagHelpers_UrlResolutionTagHelper;
        #pragma warning disable 1998
        public async override global::System.Threading.Tasks.Task ExecuteAsync()
        {
            BeginContext(32, 2, true);
            WriteLiteral("\r\n");
            EndContext();
#line 3 "C:\Users\User\Desktop\ParkBuddies_25.1\ParkBuddies\ParkBuddies\Views\User\Create.cshtml"
  
    ViewData["Title"] = "Create";

#line default
#line hidden
            BeginContext(76, 219, true);
            WriteLiteral("\r\n<h2>Add User</h2>\r\n\r\n<script src=\"/lib/jquery/dist/jquery.js\"></script>\r\n<script src=\"/lib/bootstrap/dist/js/bootstrap.js\"></script>\r\n<script src=\"/js/site.js?v=4q1jwFhaPaZgr8WAUSrux6hAuh0XDg9kPS3xIVq36I0\"></script>\r\n");
            EndContext();
            BeginContext(295, 55, false);
            __tagHelperExecutionContext = __tagHelperScopeManager.Begin("script", global::Microsoft.AspNetCore.Razor.TagHelpers.TagMode.StartTagAndEndTag, "8e470f047c6344d09dde66cf4ec31be9", async() => {
            }
            );
            __Microsoft_AspNetCore_Mvc_Razor_TagHelpers_UrlResolutionTagHelper = CreateTagHelper<global::Microsoft.AspNetCore.Mvc.Razor.TagHelpers.UrlResolutionTagHelper>();
            __tagHelperExecutionContext.Add(__Microsoft_AspNetCore_Mvc_Razor_TagHelpers_UrlResolutionTagHelper);
            __tagHelperExecutionContext.AddHtmlAttribute(__tagHelperAttribute_0);
            await __tagHelperRunner.RunAsync(__tagHelperExecutionContext);
            if (!__tagHelperExecutionContext.Output.IsContentModified)
            {
                await __tagHelperExecutionContext.SetOutputContentAsync();
            }
            Write(__tagHelperExecutionContext.Output);
            __tagHelperExecutionContext = __tagHelperScopeManager.End();
            EndContext();
            BeginContext(350, 197, true);
            WriteLiteral("\r\n\r\n<script>\r\n    $(document).ready(function () {\r\n        $(\"#adduser\").click(function () {\r\n            console.log(\"click\");\r\n            url = \"/api/Users/\";\r\n            s = \"\'{\\\"userName\\\":\\\"");
            EndContext();
            BeginContext(548, 14, false);
#line 19 "C:\Users\User\Desktop\ParkBuddies_25.1\ParkBuddies\ParkBuddies\Views\User\Create.cshtml"
                             Write(Model.UserName);

#line default
#line hidden
            EndContext();
            BeginContext(562, 536, true);
            WriteLiteral(@"\"", "" +
                ""}'"";
            callback = function (data) { alert(""User inserted.""); };
            $.ajax({
                type: ""POST"",
                url: url,
                data: s,
                headers: {
                    'Authorization': 'Bearer ' + sessionStorage.getItem(""token"").toString(),
                },
                success: callback,
                processData: false,
                contentType: 'application/json'

            });
        });
    });
</script>

<hr />

");
            EndContext();
            BeginContext(1099, 63, false);
#line 40 "C:\Users\User\Desktop\ParkBuddies_25.1\ParkBuddies\ParkBuddies\Views\User\Create.cshtml"
Write(Html.TextBox("testing", "Testing", new { @Model = "UserName" }));

#line default
#line hidden
            EndContext();
            BeginContext(1162, 6, true);
            WriteLiteral("\r\n\r\n\r\n");
            EndContext();
        }
        #pragma warning restore 1998
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.ViewFeatures.IModelExpressionProvider ModelExpressionProvider { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.IUrlHelper Url { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.IViewComponentHelper Component { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.Rendering.IJsonHelper Json { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.Rendering.IHtmlHelper<ParkBuddies.Models.User> Html { get; private set; }
    }
}
#pragma warning restore 1591